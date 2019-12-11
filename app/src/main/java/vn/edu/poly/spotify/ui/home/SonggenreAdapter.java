package vn.edu.poly.spotify.ui.home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

public class SonggenreAdapter extends RecyclerView.Adapter<SonggenreAdapter.MusicHolder>  {
    public Context context;
    public List<Music> musicList;
    public AppDataBase appDataBase;


    public SonggenreAdapter(Context context, List<Music> musicList, AppDataBase appDataBase) {
        this.context = context;
        this.musicList = musicList;
        this.appDataBase = appDataBase;

    }

    public static class MusicHolder extends RecyclerView.ViewHolder {
        public ImageView imgArtist;
        public ImageView imgLove;
        public TextView tvNameSong,tvNameArtist;
        public ConstraintLayout ctSong;
        public MusicHolder(@NonNull View itemView) {
            super(itemView);
            imgArtist=itemView.findViewById(R.id.imgArtist);
            imgLove=itemView.findViewById(R.id.imgLove);
            tvNameSong=itemView.findViewById(R.id.tvNameSong);
            tvNameArtist=itemView.findViewById(R.id.tvNameArtist);
            ctSong=itemView.findViewById(R.id.ctSong);




        }
    }
    @NonNull
    @Override
    public MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_song, parent, false);
        MusicHolder musicHolder = new MusicHolder(view);
        return musicHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MusicHolder holder, final int position) {
        final Music music = musicList.get(position);
        Glide.with(context).load(music.getImage()).into(holder.imgArtist);
        holder.tvNameSong.setText(music.getNamesong());
        holder.tvNameArtist.setText(music.getNameartist());

        holder.ctSong.setOnClickListener(new View.OnClickListener() {
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
                Log.e("Size1111111111", musicList.size() + "");
            }
        });

        holder.imgLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.e("44444", music.getId() + "");
                    long[] result = appDataBase.musicDao().insert(music);
                    if (result != null) {
                        holder.imgLove.setImageResource(R.drawable.favorite1);
                        Toast.makeText(context, "Bạn thích bài hát: " + music.namesong, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Lỗi khi thích bài hát: " + music.namesong, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "Bạn đã thích bài hát: " + music.namesong, Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return musicList.size();

    }
}
