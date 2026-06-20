package br.ifsul;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.ifsul.adaptors.AlbumAdapter;
import br.ifsul.adaptors.ArtistAdapter;
import br.ifsul.adaptors.ListViewClient;
import br.ifsul.adaptors.TrackAdapter;
import br.ifsul.communication.api.SpotifyApiClient;
import br.ifsul.communication.api.SpotifyApiService;
import br.ifsul.model.SearchResponse;
import br.ifsul.model.main.Album;
import br.ifsul.model.main.Artist;
import br.ifsul.model.main.Track;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private TextView textMessage;
    private EditText editSearch;

    private ListViewClient<Artist> artistList;
    private ListViewClient<Album> albumList;
    private ListViewClient<Track> trackList;

    SpotifyApiService spotifyService = SpotifyApiClient.getService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        createFavoriteButton();
        createSearchButton();
        createSearchLists();
    }

    private void createFavoriteButton(){
        Button buttonFavorites = findViewById(R.id.buttonFavorites);
        buttonFavorites.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });
    }

    private void createSearchButton(){
        this.textMessage = findViewById(R.id.textMessage);
        this.editSearch = findViewById(R.id.editSeach);
        Button buttonSearch = findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(this::searchSpotify);
    }

    private void createSearchLists(){
        this.artistList = new ListViewClient<>(findViewById(R.id.artistList), new ArtistAdapter(getApplicationContext()));
        this.albumList = new ListViewClient<>(findViewById(R.id.albumList), new AlbumAdapter(getApplicationContext()));
        this.trackList = new ListViewClient<>(findViewById(R.id.trackList), new TrackAdapter(getApplicationContext()));
    }

    private void searchSpotify(View view) {
        Call<SearchResponse> searchCall = spotifyService.search(this.editSearch.getText().toString());
        textMessage.setText("Carregando...");

        searchCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse searchResponse = response.body();
                if(searchResponse == null) return;

                textMessage.setText("Pesquisa: " + editSearch.getText().toString());

                if(searchResponse.getArtists() != null) {
                    artistList.addAllItems(searchResponse.getArtists().getItems());
                }
                if(searchResponse.getAlbums() != null) {
                    albumList.addAllItems(searchResponse.getAlbums().getItems());
                }
                if(searchResponse.getTracks() != null) {
                    trackList.addAllItems(searchResponse.getTracks().getItems());
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                textMessage.setText("Ocorreu algum erro: " + t.getMessage());
            }
        });
    }
}