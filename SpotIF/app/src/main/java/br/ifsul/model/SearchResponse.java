package br.ifsul.model;

import br.ifsul.model.main.Album;
import br.ifsul.model.main.Artist;
import br.ifsul.model.main.Track;

public class SearchResponse {

    private Page<Track> tracks;
    private Page<Album> albums;
    private Page<Artist> artists;

    public Page<Track> getTracks() {
        return tracks;
    }

    public void setTracks(Page<Track> tracks) {
        this.tracks = tracks;
    }

    public Page<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Page<Album> albums) {
        this.albums = albums;
    }

    public Page<Artist> getArtists() {
        return artists;
    }

    public void setArtists(Page<Artist> artists) {
        this.artists = artists;
    }
}
