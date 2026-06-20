package br.ifsul.adapters.detail;

import java.util.List;
import java.util.stream.Collectors;

import br.ifsul.model.main.Album;
import br.ifsul.model.main.Artist;
import br.ifsul.utils.DateFormatUtils;

public class AlbumDetailAdapter implements DetailItem {

    private Album album;

    public AlbumDetailAdapter(Album album) {
        this.album = album;
    }

    public DetailViewData getDetailData() {

        DetailViewData data = new DetailViewData();

        data.setTitle(album.getName());
        data.setSubtitle(album.getArtists()
                .stream().map(Artist::getName)
                .collect(Collectors.joining(", ")));
        data.setImageUrl(album.getImages().get(0).getUrl());
        data.setFields(List.of(
                "Data lançamento: " +
                        DateFormatUtils.formatBrazilDateString(album.getRelease_date()),
                "Músicas: " +
                        album.getTotal_tracks()
        ));

        return data;
    }
}
