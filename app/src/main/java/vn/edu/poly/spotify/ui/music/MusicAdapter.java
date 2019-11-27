package vn.edu.poly.spotify.ui.music;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        this.appDataBase=appDataBase;
    }
//    public ArrayList<File> findSong(File file) {
//        ArrayList<File> arrayList = new ArrayList<>();
//        File[] files = file.listFiles();
//
//        for (File singFile : files) {
//            if (singFile.isDirectory() && !singFile.isHidden()) {
//                arrayList.addAll(findSong(singFile));
//            } else {
//                if (singFile.getName().endsWith(".mp3") || singFile.getName().endsWith(".wav")) {
//                    arrayList.add(singFile);
//                }
//            }
//
//        }
//        return arrayList;
//    }




    @NonNull
    @Override
    public MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_song, parent, false);
        MusicHolder musicHolder = new MusicHolder(view);
        return musicHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MusicHolder holder, int position) {


        final Music music = musicList.get(position);
        FFmpegMediaMetadataRetriever retriever = new FFmpegMediaMetadataRetriever();
        retriever.setDataSource(music.getImage());
        byte [] datas = retriever.getEmbeddedPicture();
        Bitmap bitmap = BitmapFactory.decodeByteArray(datas, 0, datas.length);
        retriever.release();

        holder.imgArtist.setImageBitmap(bitmap);
        holder.tvNameSong.setText(music.getNamesong());
        holder.tvNameArtist.setText(music.getNameartist());

        holder.ctSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,music.namesong,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, PlayerActivity.class);
                context.startActivity(intent);
            }
        });
        holder.imgLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    long[] result=appDataBase.musicDao().insert(music);
                    if (result!=null){
                        holder.imgLove.setImageResource(R.drawable.favorite1);
                        Toast.makeText(context,"Bạn thích bài hát: "+music.namesong,Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context,"Lỗi khi thích bài hát: "+music.namesong,Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(context,"Bạn đã thích bài hát này rồi",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return musicList.size();
    }


}
