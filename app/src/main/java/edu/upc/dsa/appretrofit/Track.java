package edu.upc.dsa.appretrofit;
import java.io.Serializable;
public class Track implements Serializable {
    private int id;
    private String title;
    private String artist;
    private String album;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }

    public String getAlbum() { return album; }
    public void setAlbum(String album) { this.album = album; }
}
