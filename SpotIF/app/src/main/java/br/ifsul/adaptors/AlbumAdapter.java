
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
import br.ifsul.model.main.Album;
import br.ifsul.model.main.Artist;

public class AlbumAdapter extends ArrayAdapter<Album> {

    public AlbumAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_list_item_1);
   }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Album item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.album, parent, false);
        }

        TextView name = convertView.findViewById(R.id.name);
        TextView totalTracks = convertView.findViewById(R.id.totalTracks);
        TextView releaseDate = convertView.findViewById(R.id.releaseDate);
        TextView artists = convertView.findViewById(R.id.artists);
        ImageView imageView = convertView.findViewById(R.id.image);

        name.setText(item.getName());
        totalTracks.setText(String.valueOf(item.getTotalTracks()));
        releaseDate.setText(String.valueOf(item.getRelease_date()));

        String artistsNames = item.getArtists().stream().map(Artist::getName).collect(Collectors.joining(", "));
        artists.setText(artistsNames);

        Glide.with(this.getContext()).load(item.getImages().get(0).getUrl()).into(imageView);

        return convertView;
    }
}
