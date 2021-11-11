package com.newpathfly.flight.search.webapp.event;

import com.newpathfly.model.SearchResponse;

import org.springframework.context.ApplicationEvent;

public class SearchResponseEvent extends ApplicationEvent {

    private final SearchResponse _searchResponse;

    public SearchResponseEvent(Object source) {
        super(source);

        _searchResponse = (SearchResponse) source;
    }

    public SearchResponse getSearchResponse() {
        return _searchResponse;
    }
}
