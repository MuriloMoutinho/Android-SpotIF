
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
import br.ifsul.adapters.detail.TrackDetailAdapter;
import br.ifsul.model.main.Artist;
import br.ifsul.model.main.Track;
import br.ifsul.utils.TimeFormatUtils;

public class TrackListAdapter extends ArrayAdapter<Track> {

    public TrackListAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_list_item_1);
   }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Track item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.track, parent, false);
        }

        TextView name = convertView.findViewById(R.id.name);
        TextView artistName = convertView.findViewById(R.id.artistName);
        TextView albumName = convertView.findViewById(R.id.albumName);
        TextView duration = convertView.findViewById(R.id.duration);
        ImageView imageView = convertView.findViewById(R.id.image);

        name.setText(item.getName());
        albumName.setText(item.getAlbum().getName());
        duration.setText("Duração: " + TimeFormatUtils.formatStringTime(item.getDuration_ms()));

        String artistsNames = item.getArtists().stream().map(Artist::getName).collect(Collectors.joining(", "));
        artistName.setText(artistsNames);

        Glide.with(this.getContext()).load(item.getAlbum().getImages().get(0).getUrl()).into(imageView);

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("ITEM_DATA", new TrackDetailAdapter(item).getDetailData());
            getContext().startActivity(intent);
        });

        return convertView;
    }
}
