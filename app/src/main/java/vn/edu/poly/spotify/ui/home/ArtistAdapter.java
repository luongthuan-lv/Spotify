package vn.edu.poly.spotify.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.edu.poly.spotify.R;
import vn.edu.poly.spotify.SongArtistActivity;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistHolder> {
    Context context;
    List<Artist> artistList;

    public ArtistAdapter(Context context, List<Artist> artistList) {
        this.context = context;
        this.artistList = artistList;
    }

    @NonNull
    @Override
    public ArtistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_artist, parent, false);
        ArtistHolder artistHolder = new ArtistHolder(view);
        return artistHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistHolder holder, int position) {
        final Artist artist = artistList.get(position);
        holder.tvArtistfg.setText(artist.getName_artist());
       // Glide.with(context).load(artist.getImg_artist()).into(holder.imgCover_Artist);
        holder.cvArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SongArtistActivity.class);
                intent.putExtra("idartist", artist.getId_artist());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    public class ArtistHolder extends RecyclerView.ViewHolder {
        CardView cvArtist;
        ImageView imgCover_Artist;
        TextView tvArtistfg;
        public ArtistHolder(@NonNull View itemView) {
            super(itemView);
            cvArtist=itemView.findViewById(R.id.cvArtist);
            imgCover_Artist=itemView.findViewById(R.id.imgCover_Artist);
            tvArtistfg=itemView.findViewById(R.id.tvArtistfg);

        }
    }
}
