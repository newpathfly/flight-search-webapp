package com.newpathfly.flight.search.webapp.event;

import com.newpathfly.model.SearchResponse;

public class SearchResponseEvent implements IEvent {

    private final SearchResponse _searchResponse;

    public SearchResponseEvent(Object source) {
        _searchResponse = (SearchResponse) source;
    }

    public SearchResponse getSearchResponse() {
        return _searchResponse;
    }
}
