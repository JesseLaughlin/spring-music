package org.cloudfoundry.samples.music.web.helper;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.cloudfoundry.samples.music.config.web.WebMvcConfig;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;

@Component
public class SongsService {
    private static final String URL_INIT = "https://itunes.apple.com/search?entity=song&attribute=artistTerm&term={0}&attribute=albumTerm&term={1}";
    private static final String SPACE_CHAR = " ";
    private static final String SEND_SPACE_CHAR = "%20";
    private static final String APOST_CHAR = "'";
    private static final String SEND_APOST_CHAR = "%27";

    private static final Logger logger = LoggerFactory.getLogger(SongsService.class);

    /**
     * Makes an external call to itunes to retrieve the songs for the input artist and album title.
     * 
     * @param artist The name of the artist.
     * @param albumTitle The title of the album.
     * @return The list of songs as one String.
     */
    // @Cacheable(value = WebMvcConfig.SONG_CACHE_NAME)
    public String getSongsFromItunes(String artist, String albumTitle) {
        logger.info("Executing getSongs in SongsService");
        String output = makeItunesRequest(MessageFormat.format(URL_INIT, removeSpaces(artist), removeSpaces(albumTitle)));
        Set<String> songs = parseItunesResponse(output, albumTitle, artist);
        if (songs.size() > 0) {
            StringBuilder sb = new StringBuilder("Songs from album: ");
            int count = 0;
            for (String song : songs) {
                sb.append(song);
                sb.append(", ");
                count++;
            }
            NewRelic.addCustomParameter("SongCount: " + albumTitle, count);
            return sb.substring(0, sb.length() - 2);
        } else {
            return "Could not find any songs from that album.";
        }
    }

    private static String makeItunesRequest(String uri) {
        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpGet httpget = new HttpGet(uri);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            return httpclient.execute(httpget, responseHandler);
        } catch (Exception e) {
            logger.info("Unable to retrieve songs from itunes.", e);
        }
        return null;
    }

    private static String removeSpaces(String input) {
        if (input != null) {
            return input.trim().replaceAll(SPACE_CHAR, SEND_SPACE_CHAR).replaceAll(APOST_CHAR, SEND_APOST_CHAR);
        }
        return input;
    }

    @Trace
    private static Set<String> parseItunesResponse(String response, String origAlbum, String origArtist) {
        try {
            if (response != null) {
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(response);
                Set<String> songs = new HashSet<>();
                // Long resultCount = (Long) obj.get("resultCount");
                JSONArray results = (JSONArray) obj.get("results");
                if (results != null) {
                    Iterator<JSONObject> allResults = results.iterator();
                    while (allResults.hasNext()) {
                        JSONObject song = allResults.next();
                        String songName = (String) song.get("trackCensoredName");
                        String albumName = (String) song.get("collectionCensoredName");
                        String artistName = (String) song.get("artistName");
                        if (origArtist.equals(artistName) && origAlbum.equals(albumName)) {
                            songs.add(songName);
                        }
                    }
                }
                return songs;
            }
        } catch (Exception e) {
            logger.info("Unable to parse the json", e);
        }
        return Collections.emptySet();
    }

}
