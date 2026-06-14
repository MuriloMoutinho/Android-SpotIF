package br.ifsul.communication.api;

import br.ifsul.communication.account.SpotifyAuthInterceptor;
import br.ifsul.communication.account.SpotifyTokenAuthenticator;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SpotifyApiClient {
    private static final String BASE_URL = "https://api.spotify.com/v1/";
    public static SpotifyApiService getService() {
        SpotifyAuthInterceptor authInterceptor = new SpotifyAuthInterceptor();
        SpotifyTokenAuthenticator tokenAuthenticator = new SpotifyTokenAuthenticator(authInterceptor);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .authenticator(tokenAuthenticator)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(SpotifyApiService.class);
    }
}
