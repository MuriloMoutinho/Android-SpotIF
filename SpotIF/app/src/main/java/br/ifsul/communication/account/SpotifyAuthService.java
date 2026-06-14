package br.ifsul.communication.account;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SpotifyAuthService {

  @FormUrlEncoded
  @POST("api/token")
  Call<SpotifyToken> getAccessToken(
          @Header("Authorization") String basicAuthHeader,
          @Field("grant_type") String grantType);
}