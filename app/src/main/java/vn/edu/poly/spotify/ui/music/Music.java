package vn.edu.poly.spotify.ui.music;


import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Music implements Serializable {


    public int idsong;

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "image")
    public String image;

    @ColumnInfo(name = "namesong")
    public String namesong;

    @ColumnInfo(name = "nameartist")
    public String nameartist;

    public String data;
    public String playlist;
    public String album;
    public String genrename;

    public String getGenrename() {
        return genrename;
    }

    public void setGenrename(String genrename) {
        this.genrename = genrename;
    }

    public int getIdsong() {
        return idsong;
    }


    public void setIdsong(int idsong) {
        this.idsong = idsong;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getPlaylist() {
        return playlist;
    }

    public void setPlaylist(String playlist) {
        this.playlist = playlist;
    }

    public String getNamesong() {
        return namesong;
    }

    public void setNamesong(String namesong) {
        this.namesong = namesong;
    }

    public String getNameartist() {
        return nameartist;
    }

    public void setNameartist(String nameartist) {
        this.nameartist = nameartist;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
