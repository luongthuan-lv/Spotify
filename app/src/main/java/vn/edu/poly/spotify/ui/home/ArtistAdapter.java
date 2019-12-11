package vn.edu.poly.spotify.ui.home;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistHolder> {
    @NonNull
    @Override
    public ArtistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ArtistHolder extends RecyclerView.ViewHolder {
        public ArtistHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
