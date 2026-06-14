package br.ifsul.communication.account;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class SpotifyAuthInterceptor implements Interceptor {
    private String accessToken;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        if (accessToken == null || accessToken.isEmpty())
            return chain.proceed(originalRequest);

        Request authenticatedRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .build();

        return chain.proceed(authenticatedRequest);
    }

    public synchronized void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public synchronized String getAccessToken() {
        return this.accessToken;
    }
}
