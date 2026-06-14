
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
import br.ifsul.model.main.Album;

public class AlbumAdapter extends ArrayAdapter<Album> {

    public AlbumAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_list_item_1);
   }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Album item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.track, parent, false);
        }

        TextView titulo = convertView.findViewById(R.id.titulo);
        TextView dataLancamento = convertView.findViewById(R.id.dataLancamento);
        TextView duracao = convertView.findViewById(R.id.duracao);
        ImageView imageView = convertView.findViewById(R.id.imagem);

        titulo.setText(item.getName());
        //dataLancamento.setText(String.valueOf(item.));
        //duracao.setText(String.valueOf(item.));

        Glide.with(this.getContext()).load(item.getImages().get(0)).into(imageView);

        return convertView;
    }
}
