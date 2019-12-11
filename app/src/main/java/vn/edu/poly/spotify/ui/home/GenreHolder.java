package vn.edu.poly.spotify.ui.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.poly.spotify.R;

class GenreHolder extends RecyclerView.ViewHolder {
    CardView cvGenre;
    TextView tvNameGenre;
    ImageView imgCover_Genre;
    public GenreHolder(@NonNull View itemView) {
        super(itemView);
        cvGenre=itemView.findViewById(R.id.cvGenre);
        tvNameGenre=itemView.findViewById(R.id.tvNameGenre);
        imgCover_Genre=itemView.findViewById(R.id.imgCover_Genre);
    }
}
