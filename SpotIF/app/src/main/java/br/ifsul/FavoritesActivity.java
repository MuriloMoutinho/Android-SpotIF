package br.ifsul;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.ifsul.adapters.detail.DetailViewData;
import br.ifsul.adapters.list.FavoriteListAdapter;
import br.ifsul.adapters.list.ListViewClient;
import br.ifsul.utils.MemoryStorage;

public class FavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favorites);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        createFavoriteList();
        createBackButton();
    }

    private void createFavoriteList(){
        ListViewClient<DetailViewData> favoritesList =
                new ListViewClient<>(findViewById(R.id.favoritesList), new FavoriteListAdapter(this));

        TextView emptyText = findViewById(R.id.textEmpty);
        favoritesList.getListView().setEmptyView(emptyText);

        favoritesList.addAllItems(MemoryStorage.favorites);
    }

    private void createBackButton() {
        Button backButton = findViewById(R.id.buttonBack);
        backButton.setOnClickListener(v -> {
            finish();
        });
    }
}