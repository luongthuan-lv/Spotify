package vn.edu.poly.spotify.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import vn.edu.poly.spotify.R;


public class PlayListFragment extends Fragment {
RecyclerView rvPlaylist;
View root;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.fragment_play_list, container, false);
        rvPlaylist=root.findViewById(R.id.rvPlaylist);
        return root;
    }


}
