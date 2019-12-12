package vn.edu.poly.spotify.ui.music;

import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.spotify.R;
import vn.edu.poly.spotify.ui.home.Genre;
import wseemann.media.FFmpegMediaMetadataRetriever;

public class MusicFragment extends Fragment {


    public RecyclerView rvSong;
    public MusicAdapter musicAdapter;
    public List<Music> tempAudioList = new ArrayList<>();


    View root;

    private int genreId;
    private String genreName;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_music, container, false);
        AppDataBase appDataBase = Room.databaseBuilder(getActivity(), AppDataBase.class, "music.db").allowMainThreadQueries().build();
        rvSong = root.findViewById(R.id.rvSong);
        rvSong.setHasFixedSize(true);
        getAllAudioFromDevice1(getActivity());
        musicAdapter = new MusicAdapter(getActivity(), tempAudioList, appDataBase);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rvSong.setLayoutManager(linearLayoutManager);
        rvSong.setAdapter(musicAdapter);


        return root;
    }


    private static Uri getAlbumArtUri(int albumId) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);
    }

    public List<Music> getAllAudioFromDevice(final Context context) {

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM_ID,
        };
        Cursor c = context.getContentResolver().query(uri, projection, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                Music music = new Music();
                String path = c.getString(0);
                String album = c.getString(1);
                String artist = c.getString(2);
                String name = c.getString(3);
                String playlist = c.getString(4);


                int albumId = c.getInt(c.getColumnIndex(MediaStore.Audio.Genres.Members.ALBUM_ID));
                music.setImage(String.valueOf(getAlbumArtUri(albumId)));
                music.setNamesong(name);
                music.setAlbum(album);
                music.setNameartist(artist);
                music.setData(path);


                Log.e("Name :" + name, " Album :" + album);
                Log.e("Playlist :" + playlist, " Artist :" + artist);
                // Log.e("Data :" + data, " genres :" + genresname);
                //  Log.e("Track :" + track, " Genres :" + thisGenre);
                Log.e("+++++++++++", "++++++++++++");

                tempAudioList.add(music);

            }
            c.close();
        }

        return tempAudioList;
    }

    public List<Music> getAllAudioFromDevice1(final Context context) {

        Cursor mediaCursor;
        //  Cursor genresCursor;

        String[] mediaProjection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM_ID,

        };
//        String[] genresProjection = {
//                MediaStore.Audio.Genres.NAME,
//                MediaStore.Audio.Genres._ID
//        };

        mediaCursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                mediaProjection, null, null, null);

//        int artists = mediaCursor
//                .getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
//        int albums = mediaCursor
//                .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
//        int title = mediaCursor
//                .getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
//        int ids = mediaCursor
//                .getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
//        int datas = mediaCursor
//                .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);


        if (mediaCursor.moveToFirst()) {
            while (!mediaCursor.isAfterLast()) {
                Music music = new Music();
                String name = mediaCursor.getString(3);
                String album = mediaCursor.getString(2);
                String artist = mediaCursor.getString(1);
                String data = mediaCursor.getString(4);
                int id = mediaCursor.getInt(0);

                int albumId = mediaCursor.getInt(mediaCursor.getColumnIndex(MediaStore.Audio.Genres.Members.ALBUM_ID));
                music.setImage(String.valueOf(getAlbumArtUri(albumId)));

                music.setNamesong(name);
                music.setAlbum(album);
                music.setNameartist(artist);
                music.setData(data);
                music.setId(id);


//                int musicId = Integer.parseInt(mediaCursor.getString(ids));

//                Uri uri = MediaStore.Audio.Genres.getContentUriForAudioId("external", musicId);
//                genresCursor = context.getContentResolver().query(uri,
//                        genresProjection, null, null, null);
//                int genres = genresCursor.getColumnIndexOrThrow(MediaStore.Audio.Genres.NAME);
//
//                if (genresCursor.moveToFirst()) {
//                    do {
//                        String genre= genresCursor.getString(genres);
//                        music.setGenrename(genre);
//                    } while (genresCursor.moveToNext());
//                }
                mediaCursor.moveToNext();
                tempAudioList.add(music);
            }
//            while (mediaCursor.moveToNext());
        }


        return tempAudioList;
    }


}