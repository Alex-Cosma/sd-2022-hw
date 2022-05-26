package com.app.watchlist.model.mapper;

import com.app.watchlist.model.Watchlist;
import com.app.watchlist.model.dto.WatchlistDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WatchlistMapper {
    public WatchlistDTO toDto(Watchlist watchlist);

    public Watchlist fromDto(WatchlistDTO watchlistDTO);
}
