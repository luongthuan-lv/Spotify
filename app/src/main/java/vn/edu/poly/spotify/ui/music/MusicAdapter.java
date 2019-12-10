package vn.edu.poly.spotify.ui.music;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import vn.edu.poly.spotify.PlayerActivity;
import vn.edu.poly.spotify.R;
import wseemann.media.FFmpegMediaMetadataRetriever;

public class MusicAdapter extends RecyclerView.Adapter<MusicHolder> {
    public Context context;
    public List<Music> musicList;
    public AppDataBase appDataBase;


    public MusicAdapter(Context context, List<Music> musicList, AppDataBase appDataBase) {
        this.context = context;
        this.musicList = musicList;
        this.appDataBase = appDataBase;
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
                intent.putExtra("data",music.data);
                context.startActivity(intent);
                Log.e("Size1111111111",musicList.size()+"");
            }
        });
        holder.imgLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    long[] result = appDataBase.musicDao().insert(music);
                    if (result != null) {
                        holder.imgLove.setImageResource(R.drawable.favorite1);
                        Toast.makeText(context, "Bạn thích bài hát: " + music.namesong, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Lỗi khi thích bài hát: " + music.namesong, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "Bạn đã thích bài hát này rồi", Toast.LENGTH_SHORT).show();
                }
            }
//                if (love==false){
//
//                    appDataBase.musicDao().delete(music);
//                    musicList.remove(position);
//                    notifyDataSetChanged();
//
//                    Toast.makeText(context, "Đã bỏ thích bài hát: " + music.namesong, Toast.LENGTH_SHORT).show();
//                    love=true;
//                }


        });
    }


    @Override
    public int getItemCount() {
        return musicList.size();
    }


}
