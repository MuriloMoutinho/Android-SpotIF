
package br.ifsul.adapters.list;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import br.ifsul.R;
import br.ifsul.adapters.detail.DetailViewData;
import br.ifsul.utils.MemoryStorage;
import br.ifsul.utils.NotificationHelper;

public class FavoriteListAdapter extends ArrayAdapter<DetailViewData> {

    public FavoriteListAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_list_item_1);
   }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DetailViewData item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.favorite, parent, false);
        }

        TextView title = convertView.findViewById(R.id.title);
        TextView subtitle = convertView.findViewById(R.id.subtitle);
        TextView details = convertView.findViewById(R.id.details);
        ImageView imageView = convertView.findViewById(R.id.image);

        title.setText(item.getTitle());

        if (item.getSubtitle() != null)
            subtitle.setText(item.getSubtitle());
        else subtitle.setVisibility(View.GONE);

        if (item.getFields() != null) details.setText(String.join("\n", item.getFields()));
        else details.setVisibility(View.GONE);

        Glide.with(this.getContext()).load(item.getImageUrl()).into(imageView);

        convertView.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Confirmar exclusão")
                    .setMessage("Deseja realmente remover este item dos favoritos?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        MemoryStorage.favorites.remove(item);
                        remove(item);
                        notifyDataSetChanged();

                        Toast.makeText(v.getContext(),
                                "Item removido com sucesso!",
                                Toast.LENGTH_SHORT).show();

                        NotificationHelper.showNotification(
                                v.getContext(), "Favorito removido", item.getTitle() + " foi removido aos favoritos."
                        );
                    })
                    .setNegativeButton("Não", null)
                    .show();
        });

        return convertView;
    }
}
