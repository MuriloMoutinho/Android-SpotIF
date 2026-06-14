package br.ifsul.communication.account;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SpotifyAuthClient {
    private static final String BASE_URL = "https://accounts.spotify.com/";

    public static SpotifyAuthService getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(SpotifyAuthService.class);
    }
}
