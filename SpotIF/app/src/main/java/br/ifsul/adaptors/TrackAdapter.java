
package br.ifsul.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.util.stream.Collectors;

import br.ifsul.R;
import br.ifsul.model.main.Artist;
import br.ifsul.model.main.Track;

public class TrackAdapter extends ArrayAdapter<Track> {

    public TrackAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_list_item_1);
   }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Track item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.track, parent, false);
        }

        TextView name = convertView.findViewById(R.id.name);
        TextView albumName = convertView.findViewById(R.id.albumName);
        TextView popularity = convertView.findViewById(R.id.artists);
        TextView duration = convertView.findViewById(R.id.releaseDate);
        TextView artistName = convertView.findViewById(R.id.totalTracks);
        ImageView imageView = convertView.findViewById(R.id.image);

        name.setText(item.getName());
        albumName.setText(item.getAlbum().getName());
        popularity.setText(String.valueOf(item.getPopularity()));
        duration.setText(String.valueOf(item.getDuration_ms()));

        String artistsNames = item.getArtists().stream().map(Artist::getName).collect(Collectors.joining(", "));
        artistName.setText(artistsNames);

        Glide.with(this.getContext()).load(item.getAlbum().getImages().get(0).getUrl()).into(imageView);

        return convertView;
    }
}
