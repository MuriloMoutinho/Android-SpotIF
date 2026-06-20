package br.ifsul.adapters.detail;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import br.ifsul.model.main.Artist;
import br.ifsul.model.main.Track;
import br.ifsul.utils.TimeFormatUtils;

public class TrackDetailAdapter implements DetailItem {

    private Track track;

    public TrackDetailAdapter(Track track) {
        this.track = track;
    }

    public DetailViewData getDetailData() {

        DetailViewData data = new DetailViewData();

        data.setTitle(track.getName());
        data.setSubtitle(track.getArtists()
                .stream().map(Artist::getName)
                .collect(Collectors.joining(", ")));
        data.setImageUrl(track.getAlbum().getImages().get(0).getUrl());
        data.setFields(List.of(
                "Duração: " + TimeFormatUtils.formatStringTime(track.getDuration_ms())
        ));

        return data;
    }
}
