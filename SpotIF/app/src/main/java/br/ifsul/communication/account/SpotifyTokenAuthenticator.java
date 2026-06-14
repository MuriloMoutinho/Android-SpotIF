package br.ifsul.communication.account;

import android.util.Base64;
import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class SpotifyTokenAuthenticator implements Authenticator {
    private final SpotifyAuthInterceptor authInterceptor;
    private final String CLIENT_ID = "";

    public SpotifyTokenAuthenticator(SpotifyAuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        if (responseCount(response) >= 2) return null;

        String newAccessToken = refreshSpotifyTokenSync();

        if (newAccessToken != null) {
            authInterceptor.setAccessToken(newAccessToken);

            return response.request().newBuilder()
                    .header("Authorization", "Bearer " + newAccessToken)
                    .build();
        }

        return null;
    }

    private String refreshSpotifyTokenSync() {
        SpotifyAuthService authService = SpotifyAuthClient.getService();

        String base64Auth = Base64.encodeToString(CLIENT_ID.getBytes(), Base64.NO_WRAP);

        try {
            retrofit2.Response<SpotifyToken> tokenResponse = authService
                    .getAccessToken("Basic " + base64Auth, "client_credentials")
                    .execute();

            if (tokenResponse.isSuccessful() && tokenResponse.body() != null) {
                return tokenResponse.body().getAccessToken();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }
}

