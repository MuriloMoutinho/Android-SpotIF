package br.ifsul;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.ifsul.communication.api.SpotifyApiClient;
import br.ifsul.communication.api.SpotifyApiService;
import br.ifsul.adaptors.AlbumAdapter;
import br.ifsul.adaptors.ArtistAdapter;
import br.ifsul.adaptors.TrackAdapter;
import br.ifsul.model.SearchResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button btConectar;
    private TextView txtMsg;
    private EditText pesquisa;

    private ListView listaMusicas;
    private TrackAdapter dadosMusicas;

    private ListView listaArtistas;
    private ArtistAdapter dadosArtistas;

    private ListView listaAlbuns;
    private AlbumAdapter dadosAlbuns;

    SpotifyApiService spotifyService = SpotifyApiClient.getService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.btConectar = findViewById(R.id.btConectar);
        this.txtMsg = findViewById(R.id.txtMsg);
        this.pesquisa = findViewById(R.id.pesquisa);
        this.listaMusicas = findViewById(R.id.listaMusicas);
        this.listaArtistas = findViewById(R.id.listaArtista);
        this.listaAlbuns = findViewById(R.id.listaAlbum);

        this.dadosMusicas = new TrackAdapter(getApplicationContext());
        this.listaMusicas.setAdapter(dadosMusicas);

        this.dadosAlbuns = new AlbumAdapter(getApplicationContext());
        this.listaAlbuns.setAdapter(dadosAlbuns);

        this.dadosArtistas = new ArtistAdapter(getApplicationContext());
        this.listaArtistas.setAdapter(dadosArtistas);

        btConectar.setOnClickListener(this::conectar);
    }

    private void conectar(View view) {
        Call<SearchResponse> searchCall = spotifyService.search(this.pesquisa.getText().toString());

        searchCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse searchResponse = response.body();
                if(searchResponse == null) return;

                txtMsg.setText("Pesquisa: " + pesquisa.toString());

                dadosArtistas.addAll(searchResponse.getArtists().getItems());
                dadosArtistas.notifyDataSetChanged();

                dadosAlbuns.addAll(searchResponse.getAlbums().getItems());
                dadosAlbuns.notifyDataSetChanged();

                dadosMusicas.addAll(searchResponse.getTracks().getItems());
                dadosMusicas.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                txtMsg.setText("Ocorreu algum erro: " + t.getMessage());
            }
        });
    }
}