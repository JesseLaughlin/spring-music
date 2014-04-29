package org.cloudfoundry.samples.music.web.helper;

import java.util.Iterator;

import org.cloudfoundry.samples.music.domain.Album;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;

public class ValdateUtils {

    /**
     * Validates the input album by verifying certain fields are not null.
     * 
     * @param album The album to verify.
     * @return True if the album is valid.
     * @Throws {@link IllegalArgumentException} Thrown if the album is invalid
     */
    @Trace
    public static boolean isValid(Album album) {
        NewRelic.addCustomParameter("AlbumUpdated", album.getTitle());
        String value = album.getArtist();
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("The artist can not be null.");
        }
        value = album.getTitle();
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("The title can not be null.");
        }
        value = album.getReleaseYear();
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("The release year can not be null.");
        } else {
            try {
                int year = Integer.parseInt(value);
                if (year < 0 || year > 2014) {
                    throw new IllegalArgumentException("The release year must be a valid year (0 - 2014).");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("The year must be a valid integer.", e);
            }
        }

        return true;
    }

    /**
     * Validates that the album/artist combination does not already exist in the database.
     * 
     * @param current The album being modified.
     * @param allAlbums All of the albums in the database.
     * @return True if the current album does not already exist.
     * @throws {@link IllegalArgumentException} Thrown if the album and title already exists
     */
    @Trace
    public static boolean validateArtistAlbumDoesNotExist(Album current, Iterable<Album> allAlbums) {
        if (allAlbums != null) {
            Iterator<Album> it = allAlbums.iterator();
            while (it.hasNext()) {
                Album toCheck = it.next();
                // check to see if we are looking at the current album
                if (!current.getAlbumId().equals(toCheck.getAlbumId())) {
                    // if (!current.getId().equals(toCheck.getId())) {
                    // if we are not looking at the current album, then check if artist and album is the same
                    if (current.getArtist().equals(toCheck.getArtist())
                            && current.getTitle().equals(toCheck.getTitle())) {
                        throw new IllegalArgumentException("This album artist and title already exists.");
                    }
                }
            }
        }
        return true;
    }
}
