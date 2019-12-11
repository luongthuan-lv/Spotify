package vn.edu.poly.spotify.ui.home;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.poly.spotify.R;


public class ArtistsFragment extends Fragment {


    View root;
    RecyclerView rvArtists;
    public List<Artist> artistList;
    public Cursor albumcursor;
    public Artist artist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_artists, container, false);
        rvArtists = root.findViewById(R.id.rvArtists);
        rvArtists.setHasFixedSize(true);
        return root;
    }


}
