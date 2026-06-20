
package br.ifsul.adapters.list;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.util.stream.Collectors;

import br.ifsul.DetailsActivity;
import br.ifsul.R;
import br.ifsul.adapters.detail.AlbumDetailAdapter;
import br.ifsul.model.main.Album;
import br.ifsul.model.main.Artist;
import br.ifsul.utils.DateFormatUtils;

public class AlbumListAdapter extends ArrayAdapter<Album> {

    public AlbumListAdapter(@NonNull Context context) {
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
        TextView artists = convertView.findViewById(R.id.artistName);
        ImageView imageView = convertView.findViewById(R.id.image);

        name.setText(item.getName());
        totalTracks.setText("Músicas: " + item.getTotal_tracks());
        releaseDate.setText(DateFormatUtils.formatBrazilDateString(item.getRelease_date()));

        String artistsNames = item.getArtists().stream().map(Artist::getName).collect(Collectors.joining(", "));
        artists.setText(artistsNames);

        Glide.with(this.getContext()).load(item.getImages().get(0).getUrl()).into(imageView);

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("ITEM_DATA", new AlbumDetailAdapter(item).getDetailData());
            getContext().startActivity(intent);
        });

        return convertView;
    }
}
