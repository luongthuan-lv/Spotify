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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.spotify.R;


public class AlbumFragment extends Fragment {
    View root;
    RecyclerView rvAlbum;
    AlbumAdapter albumAdapter;
    public List<Album> albumList;
    public Cursor albumcursor;
    public Album album;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_album, container, false);
        rvAlbum = root.findViewById(R.id.rvAlbum);
        rvAlbum.setHasFixedSize(true);
        getAllAudioFromDevice1(getActivity());
        albumAdapter = new AlbumAdapter(getActivity(), albumList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        rvAlbum.setLayoutManager(linearLayoutManager);
        rvAlbum.setAdapter(albumAdapter);
        return root;
    }

    private static Uri getAlbumArtUri(int albumId) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);
    }

    public List<Album> getAllAudioFromDevice1(final Context context) {
        albumList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = context.getContentResolver();
        albumcursor = contentResolver.query(uri, null, null, null, null);
        if (albumcursor.getCount() > 0) {
            albumcursor.moveToFirst();
            while (!albumcursor.isAfterLast()) {
                album = new Album();
                album.setName_artist(albumcursor.getString(albumcursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST)));
                album.setId_album(albumcursor.getInt(albumcursor.getColumnIndex(MediaStore.Audio.Albums._ID)));
                album.setName_album(albumcursor.getString(albumcursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM)));
                album.setImage_album(String.valueOf(getAlbumArtUri(albumcursor.getInt(albumcursor.getColumnIndex(MediaStore.Audio.Albums._ID)))));
                albumList.add(album);
                albumcursor.moveToNext();
            }
        }
        albumcursor.close();
        return albumList;
    }

}
