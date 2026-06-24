package br.ifsul;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.ifsul.adapters.list.AlbumListAdapter;
import br.ifsul.adapters.list.ArtistListAdapter;
import br.ifsul.adapters.list.ListViewClient;
import br.ifsul.adapters.list.TrackListAdapter;
import br.ifsul.communication.api.SpotifyApiClient;
import br.ifsul.communication.api.SpotifyApiService;
import br.ifsul.model.Page;
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

    private TextView artistTitle;
    private TextView albumTitle;
    private TextView trackTitle;
    private ImageView imageLogo;

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
        createButtonsTexts();
        createSearchLists();
    }

    private void createFavoriteButton(){
        Button buttonFavorites = findViewById(R.id.buttonFavorites);
        buttonFavorites.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });
    }

    private void createButtonsTexts(){
        this.textMessage = findViewById(R.id.textMessage);
        this.editSearch = findViewById(R.id.editSeach);
        this.artistTitle = findViewById(R.id.labelArtists);
        this.albumTitle = findViewById(R.id.labelAlbums);
        this.trackTitle = findViewById(R.id.labelTracks);
        this.imageLogo = findViewById(R.id.imageLogo);
        Button buttonSearch = findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(this::searchSpotify);
    }

    private void createSearchLists(){
        this.artistList = new ListViewClient<>(findViewById(R.id.artistList), new ArtistListAdapter(getApplicationContext()));
        this.albumList = new ListViewClient<>(findViewById(R.id.albumList), new AlbumListAdapter(getApplicationContext()));
        this.trackList = new ListViewClient<>(findViewById(R.id.trackList), new TrackListAdapter(getApplicationContext()));
    }

    private void searchSpotify(View view) {
        Call<SearchResponse> searchCall = spotifyService.search(this.editSearch.getText().toString());
        textMessage.setText("Carregando...");

        artistList.resetAndHide();
        albumList.resetAndHide();
        trackList.resetAndHide();
        artistTitle.setVisibility(View.GONE);
        albumTitle.setVisibility(View.GONE);
        trackTitle.setVisibility(View.GONE);
        imageLogo.setVisibility(View.VISIBLE);

        searchCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse searchResponse = response.body();
                if(searchResponse == null) return;

                textMessage.setText("Pesquisa: " + editSearch.getText().toString());
                imageLogo.setVisibility(View.GONE);

                boolean isArtistsFound = isPageNotEmpty(searchResponse.getArtists());
                if(isArtistsFound) {
                    artistList.addAllAndShow(searchResponse.getArtists().getItems());
                    artistTitle.setVisibility(View.VISIBLE);
                }

                boolean isAlbumsFound = isPageNotEmpty(searchResponse.getAlbums());
                if(isAlbumsFound) {
                    albumList.addAllAndShow(searchResponse.getAlbums().getItems());
                    albumTitle.setVisibility(View.VISIBLE);
                }

                boolean isTracksFound = isPageNotEmpty(searchResponse.getTracks());
                if(isTracksFound) {
                    trackList.addAllAndShow(searchResponse.getTracks().getItems());
                    trackTitle.setVisibility(View.VISIBLE);
                }

                if(!isArtistsFound && !isAlbumsFound && !isTracksFound){
                    textMessage.setText("Sem resultados encontrados!");
                }
            }

            private boolean isPageNotEmpty(Page page){
                return page != null && page.getItems() != null && !page.getItems().isEmpty();
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                new AlertDialog.Builder(SearchActivity.this)
                        .setTitle("Erro")
                        .setMessage("Ocorreu um erro na requisição:\n\n" + t.getMessage())
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .show();
                textMessage.setText("Ocorreu um erro, tente novamente mais tarde!");
            }
        });
    }
}