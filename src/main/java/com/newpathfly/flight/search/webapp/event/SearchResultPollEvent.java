package com.newpathfly.flight.search.webapp.event;

import com.newpathfly.flight.search.webapp.event.common.IEvent;
import com.newpathfly.model.SearchResultPoll;

public class SearchResultPollEvent implements IEvent {
    
    private final SearchResultPoll _searchResultPoll;

    public SearchResultPollEvent(SearchResultPoll searchResultPoll) {
        _searchResultPoll = searchResultPoll;
    }

    public SearchResultPoll getSearchResultPoll() {
        return _searchResultPoll;
    }
}
