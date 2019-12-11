package vn.edu.poly.spotify.ui.home;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.spotify.R;
import vn.edu.poly.spotify.ui.music.Music;
import vn.edu.poly.spotify.ui.music.MusicFragment;


public class ChudeTheloaiFragment extends Fragment {


    RecyclerView rvChudetheloai;
    View root;
    private Cursor cursor;
    private List<Genre> genreList;
    GenreAdapter genreAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_chude__theloai, container, false);
        rvChudetheloai = root.findViewById(R.id.rvChudetheloai);
        rvChudetheloai.setHasFixedSize(true);
        getGenreList(getActivity());
        genreAdapter = new GenreAdapter(getActivity(), genreList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        rvChudetheloai.setLayoutManager(linearLayoutManager);
        rvChudetheloai.setAdapter(genreAdapter);

        return root;
    }


    public List<Genre> getGenreList(final Context context) {
        genreList = new ArrayList<>();
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

                cursor.moveToNext();
            }
        }
        cursor.close();
        return genreList;
    }
}
