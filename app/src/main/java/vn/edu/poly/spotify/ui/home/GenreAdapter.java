package vn.edu.poly.spotify.ui.home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.List;

import vn.edu.poly.spotify.R;
import vn.edu.poly.spotify.SongGenreActivity;
import vn.edu.poly.spotify.ui.music.Music;

public class GenreAdapter extends RecyclerView.Adapter<GenreHolder> {
    public Context context;
    public List<Genre> genreList;


    public GenreAdapter(Context context, List<Genre> genreList) {
        this.context = context;
        this.genreList = genreList;

    }

    @NonNull
    @Override
    public GenreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_chude, parent, false);
        GenreHolder genreHolder = new GenreHolder(view);
        return genreHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GenreHolder holder, int position) {
        final Genre genre = genreList.get(position);
       holder.tvNameGenre.setText(genre.getName());
        holder.cvGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, SongGenreActivity.class);
                intent.putExtra("idgenre", genre.getId());
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }
}
