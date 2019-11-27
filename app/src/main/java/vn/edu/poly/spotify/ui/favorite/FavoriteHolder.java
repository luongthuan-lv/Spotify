package vn.edu.poly.spotify.ui.favorite;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.poly.spotify.R;

class FavoriteHolder extends RecyclerView.ViewHolder {
    public ImageView imgArtistfvr,imgUnlove;
    public TextView tvNameSongfvr,tvNameArtistfvr;
    public FavoriteHolder(@NonNull View itemView) {
        super(itemView);
        imgArtistfvr=itemView.findViewById(R.id.imgArtistfvr);
        imgUnlove=itemView.findViewById(R.id.imgUnlove);
        tvNameSongfvr=itemView.findViewById(R.id.tvNameSongfvr);
        tvNameArtistfvr=itemView.findViewById(R.id.tvNameArtistfvr);
    }
}
