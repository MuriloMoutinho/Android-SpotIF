package br.ifsul;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.util.Objects;

import br.ifsul.adapters.detail.DetailViewData;
import br.ifsul.utils.MemoryStorage;
import br.ifsul.utils.NotificationHelper;

public class DetailsActivity extends AppCompatActivity {

    private DetailViewData item;
    private Button favoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        favoriteButton = findViewById(R.id.buttonFavoriteDetail);

        processItem();
        createFavoriteButton();
        createBackButton();
    }

    private void processItem(){
        ImageView image = findViewById(R.id.imageDetail);
        TextView title = findViewById(R.id.titleDetail);
        TextView subtitle = findViewById(R.id.subtitleDetail);
        TextView details = findViewById(R.id.details);

        Intent intent = getIntent();
        item = (DetailViewData) intent.getSerializableExtra("ITEM_DATA");

        Glide.with(getApplicationContext()).load(item.getImageUrl()).into(image);
        title.setText(item.getTitle());

        if(item.getSubtitle() != null) subtitle.setText(item.getSubtitle());
        else subtitle.setVisibility(View.GONE);

        if(item.getFields() != null) details.setText(String.join("\n", item.getFields()));
        else details.setVisibility(View.GONE);

        if (isFavoritesContainsItem())
            favoriteButton.setText("REMOVER DOS FAVORITOS");
    }

    private boolean isFavoritesContainsItem(){
        return MemoryStorage.favorites
                .stream()
                .anyMatch(i -> Objects.equals(i.getTitle(), item.getTitle()));
    }

    private void createFavoriteButton(){

        favoriteButton.setOnClickListener(v -> {
            if (!isFavoritesContainsItem()) {
                MemoryStorage.favorites.add(item);
                Toast.makeText(this,
                                "Adicionado aos favoritos", Toast.LENGTH_SHORT)
                        .show();
                favoriteButton.setText("REMOVER DOS FAVORITOS");

                NotificationHelper.showNotification(
                        this, "Favorito adicionado", item.getTitle() + " foi adicionado aos favoritos."
                );
            } else {
                MemoryStorage.favorites.remove(item);
                Toast.makeText(this,
                                "Removido dos favoritos", Toast.LENGTH_SHORT)
                        .show();
                favoriteButton.setText("ADICIONAR AOS FAVORITOS");

                NotificationHelper.showNotification(
                        this, "Favorito removido", item.getTitle() + " foi removido aos favoritos."
                );
            }
        });
    }

    private void createBackButton(){
        Button backButton = findViewById(R.id.buttonBack);
        backButton.setOnClickListener(v -> {
            finish();
        });
    }
}