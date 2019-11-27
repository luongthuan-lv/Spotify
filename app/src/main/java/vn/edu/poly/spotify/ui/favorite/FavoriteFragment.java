package vn.edu.poly.spotify.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

import vn.edu.poly.spotify.R;
import vn.edu.poly.spotify.ui.music.AppDataBase;
import vn.edu.poly.spotify.ui.music.Music;

public class FavoriteFragment extends Fragment {
    RecyclerView rvFavorite;
    List<Music> musicList;
    FavoriteAdapter favoriteAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_favorite, container, false);
        rvFavorite=root.findViewById(R.id.rvFavorite);
        AppDataBase appDataBase = Room.databaseBuilder(getActivity(), AppDataBase.class, "music.db").allowMainThreadQueries().build();
        musicList = appDataBase.musicDao().getAll();
        rvFavorite.setHasFixedSize(true);
        favoriteAdapter = new FavoriteAdapter(getActivity(), musicList, appDataBase);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rvFavorite.setLayoutManager(linearLayoutManager);
        rvFavorite.setAdapter(favoriteAdapter);

        return root;
    }
}
