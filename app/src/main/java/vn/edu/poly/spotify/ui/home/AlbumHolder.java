package vn.edu.poly.spotify.ui.home;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.poly.spotify.R;

class AlbumHolder extends RecyclerView.ViewHolder {
    CardView cvAlbum;
    ImageView imgPicture;
    TextView tvSong,tvArtist;

    public AlbumHolder(@NonNull View itemView) {
        super(itemView);
        cvAlbum=itemView.findViewById(R.id.cvAlbum);
        imgPicture=itemView.findViewById(R.id.imgPicture);
        tvSong=itemView.findViewById(R.id.tvSong);
        tvArtist=itemView.findViewById(R.id.tvArtist);

    }
}
