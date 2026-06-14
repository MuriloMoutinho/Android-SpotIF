package br.ifsul.communication.api;

import br.ifsul.model.SearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SpotifyApiService {

  @GET("search?type=album%2Cartist%2Cplaylist&")
  Call<SearchResponse> search(@Query("q") String term);
}