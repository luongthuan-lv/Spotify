package vn.edu.poly.spotify.ui.music;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.spotify.PlayerActivity;
import vn.edu.poly.spotify.R;
import vn.edu.poly.spotify.SongGenreActivity;
import wseemann.media.FFmpegMediaMetadataRetriever;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicHolder> implements Filterable {
    public Context context;
    public List<Music> musicList;
    public List<Music> musicListFull;
    public AppDataBase appDataBase;



    public MusicAdapter(Context context, List<Music> musicList, AppDataBase appDataBase) {
        this.context = context;
        this.musicList = musicList;
        this.appDataBase = appDataBase;
        musicListFull = new ArrayList<>(musicList);

    }

    public static class MusicHolder extends RecyclerView.ViewHolder {
        public ImageView imgArtist;
        public ImageView imgLove;
        public TextView tvNameSong, tvNameArtist;
        public ConstraintLayout ctSong;

        public MusicHolder(@NonNull View itemView) {
            super(itemView);
            imgArtist = itemView.findViewById(R.id.imgArtist);
            imgLove = itemView.findViewById(R.id.imgLove);
            tvNameSong = itemView.findViewById(R.id.tvNameSong);
            tvNameArtist = itemView.findViewById(R.id.tvNameArtist);
            ctSong = itemView.findViewById(R.id.ctSong);
        }
    }

    @NonNull
    @Override
    public MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_song, parent, false);
        MusicHolder musicHolder = new MusicHolder(view);
        return musicHolder;
    }

    //    private void Bitmap(){
//        final Music music = musicList.get(position);
//        FFmpegMediaMetadataRetriever retriever = new FFmpegMediaMetadataRetriever();
//        retriever.setDataSource(music.getImage());
//        byte[] datas = retriever.getEmbeddedPicture();
//        Bitmap bitmap = BitmapFactory.decodeByteArray(datas, 0, datas.length);
//        retriever.release()
//
//    }
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
                    Toast.makeText(context, "Bạn đã thích bài hát này ", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


    @Override
    public int getItemCount() {
        return musicList.size();
    }

    @Override
    public Filter getFilter() {
        return bookHomeFilter;
    }

    private Filter bookHomeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Music> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(musicListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Music item : musicListFull) {
                    if (item.getNamesong().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            musicList.clear();
            musicList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


}
