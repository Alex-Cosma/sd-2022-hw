package com.lab4.demo.batch;


import com.lab4.demo.track.model.Track;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

public class TrackBatchItemProcessor implements ItemProcessor<Track, Track> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrackBatchItemProcessor.class);

    @Override
    public Track process(final Track trackBatch) throws Exception {
        Long id = trackBatch.getId();
        System.out.println("Processing TrackBatch: " + id);
        String title = trackBatch.getTitle();
        String artist = trackBatch.getArtist();
        String album = trackBatch.getAlbum();
        Boolean explicit_lyrics = trackBatch.getExplicit_lyrics();
        String preview = trackBatch.getPreview();
        String link =trackBatch.getLink();
        int duration = trackBatch.getDuration();

        Track transformedTrackBatch = new Track(id, artist, album, duration, explicit_lyrics, preview, link, title);
        LOGGER.info("Converting ( {} ) into ( {} )", trackBatch, transformedTrackBatch);

        return transformedTrackBatch;
    }
}