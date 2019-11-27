package vn.edu.poly.spotify.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.poly.spotify.R;


public class AlbumFragment extends Fragment {
View root;
RecyclerView rvAlbum;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.fragment_album, container, false);
        rvAlbum=root.findViewById(R.id.rvAlbum);
        return root;
    }

}
