package vn.edu.poly.spotify.ui.music;

import android.content.ContentUris;
import android.content.Context;
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
import wseemann.media.FFmpegMediaMetadataRetriever;

public class MusicFragment extends Fragment {


    public RecyclerView rvSong;
    public MusicAdapter musicAdapter;
    List<Music> tempAudioList = new ArrayList<>();
    View root;
    MediaMetadataRetriever mmr = new MediaMetadataRetriever();
    private int genreId;
    private String genreName;




//    public GenresFragment(int genreId, String genreName) {
//        this.genreId = genreId;
//        this.genreName = genreName;
//    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_music, container, false);
        AppDataBase appDataBase = Room.databaseBuilder(getActivity(), AppDataBase.class, "music.db").allowMainThreadQueries().build();
        rvSong = root.findViewById(R.id.rvSong);
        rvSong.setHasFixedSize(true);
        getAllAudioFromDevice(getActivity());
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
                MediaStore.Audio.Media.DISPLAY_NAME,

                };


        Cursor c = context.getContentResolver().query(uri, projection, null, null, null);

        if (c != null && c.getCount()>0) {
            while (c.moveToNext()) {

                Music music = new Music();
                String path = c.getString(0);
                String album = c.getString(1);
                String artist = c.getString(2);
                String name = c.getString(3);
                String playlist = c.getString(4);
                String data=c.getString(5);

               // String genres=c.getString(8);

                int albumId = c.getInt(c.getColumnIndex(MediaStore.Audio.Genres.Members.ALBUM_ID));
                music.setImage(String.valueOf(getAlbumArtUri(albumId)));
                music.setNamesong(name);
                music.setAlbum(album);
                music.setNameartist(artist);
                music.setData(path);
                //music.setId(id);


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



    public List<Music> getMusicList(final Context context) {

       Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Genres.Members.getContentUri("external", genreId), null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Music music = new Music();
                music.setGenrename(genreName);
                music.setNamesong(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Genres.Members.TITLE)));
                int albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Genres.Members.ALBUM_ID));
                music.setImage(String.valueOf(getAlbumArtUri(albumId)));
                music.setNameartist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Genres.Members.ARTIST)));
              //  music.setTime(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Genres.Members.DURATION)));
                music.setImage(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Genres.Members.DATA)));
                music.setAlbum(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM)));
                tempAudioList.add(music);
                cursor.moveToNext();
                Log.e("Name :" + cursor.getColumnIndex(MediaStore.Audio.Genres.Members.TITLE), " Artist :" + cursor.getColumnIndex(MediaStore.Audio.Genres.Members.ARTIST));

            }
            cursor.moveToNext();
        }
        cursor.close();
        return tempAudioList;
    }


//    public void getSongs() {
//        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//
//
//        ContentResolver contentResolver = getActivity().getContentResolver();
//        Cursor myCursor;
//
//
//        String img = MediaStore.Audio.Media._ID;
//        String data = MediaStore.Audio.Media.DATA;
//        String namesong = MediaStore.Audio.Media.DISPLAY_NAME;
//        String nameartist = MediaStore.Audio.Media.ARTIST;
//        String[] songColums = {img, namesong, nameartist, data};
//
//        final String musicOnly = MediaStore.Audio.Media.IS_MUSIC + "=1";
//
//        myCursor = contentResolver.query(songUri, songColums, musicOnly, null, null);
//
//        if (myCursor != null && myCursor.moveToFirst()) {
//            String songId = myCursor.getString(myCursor.getColumnIndexOrThrow(img));
//            String songData = myCursor.getString(myCursor.getColumnIndexOrThrow(data));
//            String songTitle = myCursor.getString(myCursor.getColumnIndexOrThrow(namesong));
//            String songArtist = myCursor.getString(myCursor.getColumnIndexOrThrow(nameartist));
//
//            do {
//
//                Music song = new Music();
//                song.setData(songData);
//                song.setImage(songId);
//                song.setNamesong(songTitle);
//                song.setNameartist(songArtist);
//
//                musicList.add(song);
//
//            } while (myCursor.moveToNext());
//        } else {
//
//        }
//        myCursor.close();
//    }


}