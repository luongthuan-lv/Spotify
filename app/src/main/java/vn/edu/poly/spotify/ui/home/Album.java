package vn.edu.poly.spotify.ui.home;

public class Album {
    String name_album;


    String name_artist;
    String image_album;
    int  id_album;

    public int getId_album() {
        return id_album;
    }

    public void setId_album(int id_album) {
        this.id_album = id_album;
    }

    public String getName_album() {
        return name_album;
    }

    public void setName_album(String name_album) {
        this.name_album = name_album;
    }


    public String getImage_album() {
        return image_album;
    }

    public String getName_artist() {
        return name_artist;
    }

    public void setName_artist(String name_artist) {
        this.name_artist = name_artist;
    }
    public void setImage_album(String image_album) {
        this.image_album = image_album;
    }
}
