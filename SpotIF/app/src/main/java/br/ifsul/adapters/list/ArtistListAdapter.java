
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

import br.ifsul.DetailsActivity;
import br.ifsul.R;
import br.ifsul.adapters.detail.ArtistDetailAdapter;
import br.ifsul.model.main.Artist;

public class ArtistListAdapter extends ArrayAdapter<Artist> {

    public ArtistListAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_list_item_1);
   }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Artist item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.artist, parent, false);
        }

        TextView name = convertView.findViewById(R.id.name);
        ImageView imageView = convertView.findViewById(R.id.image);

        name.setText(item.getName());
        Glide.with(this.getContext()).load(item.getImages().get(0).getUrl()).into(imageView);

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("ITEM_DATA", new ArtistDetailAdapter(item).getDetailData());
            getContext().startActivity(intent);
        });

        return convertView;
    }
}
