
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

import br.ifsul.R;
import br.ifsul.model.main.Artist;

public class ArtistAdapter extends ArrayAdapter<Artist> {

    public ArtistAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_list_item_1);
   }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Artist item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.artist, parent, false);
        }

        TextView name = convertView.findViewById(R.id.name);
        TextView followers = convertView.findViewById(R.id.totalTracks);
        TextView popularity = convertView.findViewById(R.id.artists);
        TextView genres = convertView.findViewById(R.id.releaseDate);
        ImageView imageView = convertView.findViewById(R.id.image);

        name.setText(item.getName());
        followers.setText(String.valueOf(item.getFollowers()));
        popularity.setText(String.valueOf(item.getPopularity()));
        if(item.getGenres() != null){
            genres.setText(String.join(", ", item.getGenres()));
        }

        Glide.with(this.getContext()).load(item.getImages().get(0).getUrl()).into(imageView);

        return convertView;
    }
}
