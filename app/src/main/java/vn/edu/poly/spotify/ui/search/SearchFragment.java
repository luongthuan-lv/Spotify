package vn.edu.poly.spotify.ui.search;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.spotify.R;
import vn.edu.poly.spotify.ui.music.AppDataBase;
import vn.edu.poly.spotify.ui.music.Music;
import vn.edu.poly.spotify.ui.music.MusicAdapter;
import vn.edu.poly.spotify.ui.music.MusicFragment;

public class SearchFragment extends Fragment {


    android.widget.SearchView searchView;
    RecyclerView rvSearchView;
    MusicAdapter musicAdapter;

    public List<Music> tempAudioList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        rvSearchView = root.findViewById(R.id.rvSearchView);
        searchView = root.findViewById(R.id.searchView);
        rvSearchView.setHasFixedSize(true);
        AppDataBase appDataBase = Room.databaseBuilder(getActivity(), AppDataBase.class, "music.db").allowMainThreadQueries().build();
        getAllAudioFromDevice1(getActivity());
        musicAdapter = new MusicAdapter(getActivity(), tempAudioList, appDataBase);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rvSearchView.setLayoutManager(linearLayoutManager);
        rvSearchView.setAdapter(musicAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                musicAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return root;
    }

    private static Uri getAlbumArtUri(int albumId) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);
    }


    public List<Music> getAllAudioFromDevice1(final Context context) {

        Cursor mediaCursor;
        Cursor genresCursor;

        String[] mediaProjection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM_ID
        };
        String[] genresProjection = {
                MediaStore.Audio.Genres.NAME,
                MediaStore.Audio.Genres._ID
        };

        mediaCursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                mediaProjection, null, null, null);

        int artists = mediaCursor
                .getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
        int albums = mediaCursor
                .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
        int title = mediaCursor
                .getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
        int id = mediaCursor
                .getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
        int datas = mediaCursor
                .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);


        if (mediaCursor.moveToFirst()) {
            do {
                Music music = new Music();
                String name = mediaCursor.getString(title);
                String album =mediaCursor.getString(albums);
                String artist= mediaCursor.getString(artists);
                String data=mediaCursor.getString(datas);


                int albumId = mediaCursor.getInt(mediaCursor.getColumnIndex(MediaStore.Audio.Genres.Members.ALBUM_ID));
                music.setImage(String.valueOf(getAlbumArtUri(albumId)));
                music.setNamesong(name);
                music.setAlbum(album);
                music.setNameartist(artist);
                music.setData(data);


                int musicId = Integer.parseInt(mediaCursor.getString(id));
                Uri uri = MediaStore.Audio.Genres.getContentUriForAudioId("external", musicId);
                genresCursor = context.getContentResolver().query(uri,
                        genresProjection, null, null, null);
                int genres = genresCursor.getColumnIndexOrThrow(MediaStore.Audio.Genres.NAME);

                if (genresCursor.moveToFirst()) {
                    do {
                        String genre= genresCursor.getString(genres);
                        music.setGenrename(genre);
                    } while (genresCursor.moveToNext());
                }
                tempAudioList.add(music);
            } while (mediaCursor.moveToNext());
        }


        return tempAudioList;
    }
}