package vn.edu.poly.spotify.ui.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import vn.edu.poly.spotify.R;

class PlaylistHolder extends RecyclerView.ViewHolder {
    public ImageView imgBackground_playlist,imgPlaylist;
    public TextView tvNamePlaylist;
    public PlaylistHolder(@NonNull View itemView) {
        super(itemView);
        imgBackground_playlist=itemView.findViewById(R.id.imgBackground_playlist);
        imgPlaylist=itemView.findViewById(R.id.imgPlaylist);
        tvNamePlaylist=itemView.findViewById(R.id.tvNamePlaylist);
    }
}
