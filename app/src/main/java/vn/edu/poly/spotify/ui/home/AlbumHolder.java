package vn.edu.poly.spotify.ui.home;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.poly.spotify.R;

public class AlbumHolder extends RecyclerView.ViewHolder {
    CardView cvAlbums;
    ImageView imgPictures;
    TextView tvSongs,tvArtists;

    public AlbumHolder(@NonNull View itemView) {
        super(itemView);
        cvAlbums=itemView.findViewById(R.id.cvAlbums);
        imgPictures=itemView.findViewById(R.id.imgPictures);
        tvSongs=itemView.findViewById(R.id.tvSongs);
        tvArtists=itemView.findViewById(R.id.tvArtists);

    }
}
