package vn.edu.poly.spotify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.spotify.ui.home.Album;
import vn.edu.poly.spotify.ui.home.SonggenreAdapter;
import vn.edu.poly.spotify.ui.music.AppDataBase;
import vn.edu.poly.spotify.ui.music.Music;

public class SongAlbumActivity extends AppCompatActivity {
    RecyclerView rvSongAlbum;
    List<Music> listArrayList;
    private Cursor cursor;
    private List<Album> albumList;
    int id_catealbum;
    SonggenreAdapter songgenreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_album);
        rvSongAlbum=findViewById(R.id.rvSongAlbum);
        rvSongAlbum.setHasFixedSize(true);
        Intent intent = getIntent();
        id_catealbum = intent.getIntExtra("idalbum", 0);
        AppDataBase appDataBase = Room.databaseBuilder(SongAlbumActivity.this, AppDataBase.class, "music.db").allowMainThreadQueries().build();
        getAlbumList(SongAlbumActivity.this,id_catealbum);
        songgenreAdapter = new SonggenreAdapter(SongAlbumActivity.this, listArrayList, appDataBase) {
        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SongAlbumActivity.this, RecyclerView.VERTICAL, false);
        rvSongAlbum.setLayoutManager(linearLayoutManager);
        rvSongAlbum.setAdapter(songgenreAdapter);
    }

    private static Uri getAlbumArtUri(int albumId) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);
    }

    public List<Album> getAlbumList(final Context context, int cateId) {

        albumList = new ArrayList<>();
        listArrayList = new ArrayList<>();

        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = context.getContentResolver();
        cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Album album = new Album();
                album.setId_album(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums._ID)));
                album.setName_album(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM)));
                album.setName_artist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST)));
                albumList.add(album);


                if (album.getId_album() == cateId) {
                    String selection = "is_music != 0";

                    if (album.getId_album() > 0) {
                        selection = selection + " and album_id = " + album.getId_album();
                }
                    Uri uri1 = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    String[] projection = new String[]{
                            MediaStore.Audio.Media.TITLE,
                            MediaStore.Audio.Media._ID,
                            MediaStore.Audio.Genres.Members.ARTIST,
                            MediaStore.Audio.Genres.Members.ALBUM_ID,
                            MediaStore.Audio.Media.DATA};
                    final String sortOrder = MediaStore.Audio.AudioColumns.TITLE + " COLLATE LOCALIZED ASC";
                    Cursor songCursor = contentResolver.query(uri1, projection, selection, null, sortOrder);
                    if (songCursor.getCount() > 0) {
                        songCursor.moveToFirst();
                        while (!songCursor.isAfterLast()) {
                            Music music = new Music();
                            int albumId = songCursor.getInt(3);
                            music.setImage(String.valueOf(getAlbumArtUri(albumId)));
                            music.setId(songCursor.getInt(1));
                            music.setNamesong(songCursor.getString(0));
                            music.setNameartist(songCursor.getString(2));
                            music.setData(songCursor.getString(4));
                            listArrayList.add(music);
                            songCursor.moveToNext();

                        }
                        songCursor.moveToNext();
                    }
                    songCursor.close();
                    break;
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        return albumList;
    }
}
