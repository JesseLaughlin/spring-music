package org.cloudfoundry.samples.music.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Album {
    @Id
    @Column(length = 40)
    @GeneratedValue(generator = "randomId")
    @GenericGenerator(name = "randomId", strategy = "org.cloudfoundry.samples.music.domain.RandomIdGenerator")
    private String id;

    private String title;
    private String artist;
    private String releaseYear;
    private String genre;
    private int trackCount;
    private String albumId;
    private String songs;

    public Album() {
    }

    public Album(String title, String artist, String releaseYear, String genre) {
        this.title = title;
        this.artist = artist;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getTrackCount() {
        return trackCount;
    }

    public void setTrackCount(int trackCount) {
        this.trackCount = trackCount;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    /**
     * Gets the field songs.
     * 
     * @return the songs
     */
    public String getSongs() {
        return songs;
    }

    /**
     * Sets the field songs.
     * 
     * @param songs the songs to set
     */
    public void setSongs(String songs) {
        this.songs = songs;
    }

}
