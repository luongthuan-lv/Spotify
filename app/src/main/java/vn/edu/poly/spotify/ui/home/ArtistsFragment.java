package vn.edu.poly.spotify.ui.home;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.spotify.R;


public class ArtistsFragment extends Fragment {


    View root;
    RecyclerView rvArtists;
    public List<Artist> artistList;
    public Cursor albumcursor;
    public Artist artist;
    ArtistAdapter artistAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_artists, container, false);
        rvArtists = root.findViewById(R.id.rvArtists);
        rvArtists.setHasFixedSize(true);
        getAllAudioFromDevice1(getActivity());
        artistAdapter = new ArtistAdapter(getActivity(), artistList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        rvArtists.setLayoutManager(linearLayoutManager);
        rvArtists.setAdapter(artistAdapter);
        return root;
    }

    private static Uri getAlbumArtUri(int albumId) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);
    }

    public List<Artist> getAllAudioFromDevice1(final Context context) {
        artistList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = context.getContentResolver();
        albumcursor = contentResolver.query(uri, null, null, null, null);
        if (albumcursor.getCount() > 0) {
            albumcursor.moveToFirst();
            while (!albumcursor.isAfterLast()) {
                artist = new Artist();
                artist.setName_artist(albumcursor.getString(albumcursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST)));
                artist.setId_artist(albumcursor.getInt(albumcursor.getColumnIndex(MediaStore.Audio.Artists._ID)));
              //  artist.setImg_artist(String.valueOf(getAlbumArtUri(albumcursor.getInt(albumcursor.getColumnIndex(MediaStore.Audio.Artists._ID)))));
                artistList.add(artist);
                albumcursor.moveToNext();
            }
        }
        albumcursor.close();
        return artistList;
    }


}
