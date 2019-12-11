package vn.edu.poly.spotify.ui.home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import vn.edu.poly.spotify.R;


public class AlbumAdapter extends RecyclerView.Adapter<AlbumHolder> {
    Context context;
    List<Album> albumList;

    public AlbumAdapter(Context context, List<Album> albumList) {
        this.context = context;
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_album, parent, false);
        AlbumHolder albumHolder = new AlbumHolder(view);
        return albumHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumHolder holder, int position) {

        final Album album = albumList.get(position);
        holder.tvSong.setText(album.getName_album());
        holder.tvArtist.setText(album.getName_artist());
        Glide.with(context).load(album.getImage_album()).into(holder.imgPicture);
        holder.cvAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SongAlbumActivity.class);
                intent.putExtra("idalbum", album.getId_album());
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
