package br.ifsul.adapters.detail;

import br.ifsul.model.main.Artist;

public class ArtistDetailAdapter implements DetailItem {

    private Artist artist;

    public ArtistDetailAdapter(Artist artist) {
        this.artist = artist;
    }

    public DetailViewData getDetailData() {

        DetailViewData data = new DetailViewData();

        data.setTitle(artist.getName());
        data.setImageUrl(artist.getImages().get(0).getUrl());

        return data;
    }
}
