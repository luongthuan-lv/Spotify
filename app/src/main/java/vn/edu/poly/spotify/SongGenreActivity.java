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
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.spotify.ui.home.Genre;
import vn.edu.poly.spotify.ui.home.SonggenreAdapter;
import vn.edu.poly.spotify.ui.music.AppDataBase;
import vn.edu.poly.spotify.ui.music.Music;
import vn.edu.poly.spotify.ui.music.MusicAdapter;

public class SongGenreActivity extends AppCompatActivity {
    RecyclerView rvSongGenre;
    List<Music> listArrayList;
    SonggenreAdapter songgenreAdapter;
    private Cursor cursor;
    private List<Genre> genreList;
    int id_cate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songgenre);
        rvSongGenre = findViewById(R.id.rvSongGenre);

        Intent intent = getIntent();
        id_cate = intent.getIntExtra("idgenre", 0);
        AppDataBase appDataBase = Room.databaseBuilder(SongGenreActivity.this, AppDataBase.class, "music.db").allowMainThreadQueries().build();
        getGenreList(SongGenreActivity.this, id_cate);
        songgenreAdapter = new SonggenreAdapter(SongGenreActivity.this, listArrayList, appDataBase) {
        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SongGenreActivity.this, RecyclerView.VERTICAL, false);
        rvSongGenre.setLayoutManager(linearLayoutManager);
        rvSongGenre.setAdapter(songgenreAdapter);
    }

    private static Uri getAlbumArtUri(int albumId) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);
    }


    public List<Genre> getGenreList(final Context context, int cateId) {

        genreList = new ArrayList<>();
        listArrayList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = context.getContentResolver();
        cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Genre genre = new Genre();
                genre.setId(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Genres._ID)));
                genre.setName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Genres.NAME)));
                genreList.add(genre);
                Log.e("2222222222",genre.getId()+"");

                if (genre.getId() == cateId) {
                    Uri uri1 = MediaStore.Audio.Genres.Members.getContentUri("external", genre.getId());
                    String[] projection = new String[]{
                            MediaStore.Audio.Media.TITLE,
                            MediaStore.Audio.Media._ID,
                            MediaStore.Audio.Genres.Members.ARTIST,
                            MediaStore.Audio.Genres.Members.ALBUM_ID,
                            MediaStore.Audio.Media.DATA};
                    Cursor songCursor = contentResolver.query(uri1, projection, null, null, null);
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
                            Log.e("123456789", music.getId() + "");
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
        return genreList;
    }
}
