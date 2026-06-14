package br.ifsul.communication.account;

public class SpotifyToken {
    private String access_token;
    private String token_type;
    private int expires_in;

    public String getAccessToken() {
        return access_token;
    }
    public String getTokenType() {
        return token_type;
    }
}
