package vn.edu.poly.spotify.ui.music;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import vn.edu.poly.spotify.R;
import wseemann.media.FFmpegMediaMetadataRetriever;

class MusicHolder extends RecyclerView.ViewHolder {
    ImageView imgArtist,imgLove;
    TextView tvNameSong,tvNameArtist;
    ConstraintLayout ctSong;
    public MusicHolder(@NonNull View itemView) {
        super(itemView);
        imgArtist=itemView.findViewById(R.id.imgArtist);
        imgLove=itemView.findViewById(R.id.imgLove);
        tvNameSong=itemView.findViewById(R.id.tvNameSong);
        tvNameArtist=itemView.findViewById(R.id.tvNameArtist);
        ctSong=itemView.findViewById(R.id.ctSong);



    }
}
