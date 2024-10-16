package edu.upc.dsa.appretrofit;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder> {

    private List<Track> trackList;
    private OnTrackClickListener onTrackClickListener;

    // Interface para manejar el clic en cada pista
    public interface OnTrackClickListener {
        void onTrackClick(Track track);
    }

    public TrackAdapter(List<Track> trackList, OnTrackClickListener listener) {
        this.trackList = trackList;
        this.onTrackClickListener = listener;
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.track_item, parent, false);
        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        Track track = trackList.get(position);
        holder.bind(track, onTrackClickListener);
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    public static class TrackViewHolder extends RecyclerView.ViewHolder {
        TextView title, artist;

        public TrackViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_title);
            artist = itemView.findViewById(R.id.text_artist);
        }

        public void bind(Track track, OnTrackClickListener listener) {
            title.setText(track.getTitle());
            artist.setText(track.getArtist());

            // Configurar el clic en cada elemento
            itemView.setOnClickListener(v -> listener.onTrackClick(track));
        }
    }
}
