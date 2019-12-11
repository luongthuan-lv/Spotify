package vn.edu.poly.spotify.ui.favorite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.List;

import vn.edu.poly.spotify.PlayerActivity;
import vn.edu.poly.spotify.R;
import vn.edu.poly.spotify.ui.music.AppDataBase;
import vn.edu.poly.spotify.ui.music.Music;
import wseemann.media.FFmpegMediaMetadataRetriever;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder> {
    public Context context;
    public List<Music> musicList;
    public AppDataBase appDataBase;

    public FavoriteAdapter(Context context, List<Music> musicList, AppDataBase appDataBase) {
        this.context = context;
        this.musicList = musicList;
        this.appDataBase = appDataBase;
    }

    class FavoriteHolder extends RecyclerView.ViewHolder {
        public ImageView imgArtistfvr,imgUnlove;
        public TextView tvNameSongfvr,tvNameArtistfvr;
        public ConstraintLayout ctSongFavorite;
        public FavoriteHolder(@NonNull View itemView) {
            super(itemView);
            imgArtistfvr=itemView.findViewById(R.id.imgArtistfvr);
            imgUnlove=itemView.findViewById(R.id.imgUnlove);
            tvNameSongfvr=itemView.findViewById(R.id.tvNameSongfvr);
            tvNameArtistfvr=itemView.findViewById(R.id.tvNameArtistfvr);
            ctSongFavorite=itemView.findViewById(R.id.ctSongFavorite);
        }
    }
    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_favorite, parent, false);
        FavoriteHolder favoriteHolder = new FavoriteHolder(view);
        return favoriteHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHolder holder, final int position) {
        final Music music = musicList.get(position);
        holder.tvNameSongfvr.setText(music.namesong);
        holder.tvNameArtistfvr.setText(music.nameartist);
        Glide.with(context).load(music.getImage()).into(holder.imgArtistfvr);
        holder.imgUnlove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bỏ thích bài hát?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Đã hủy thao tác!", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        appDataBase.musicDao().delete(music);
                        musicList.remove(position);
                        notifyDataSetChanged();

                        Toast.makeText(context, "Đã bỏ thích bài hát: " + music.namesong, Toast.LENGTH_SHORT).show();
                    }
                });

                builder.create();
                builder.show();
            }
        });

        holder.ctSongFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, music.namesong, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra("songname", music.namesong);
                intent.putExtra("songimage", music.image);
                intent.putExtra("nameartist", music.nameartist);
                intent.putExtra("size", musicList.size());
                intent.putExtra("musiclist", (Serializable) musicList);
                intent.putExtra("pos", position);
                intent.putExtra("data", music.data);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }
}
