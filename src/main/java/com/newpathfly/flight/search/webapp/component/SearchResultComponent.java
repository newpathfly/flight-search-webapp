package com.newpathfly.flight.search.webapp.component;

import com.newpathfly.api.ShoppingApi;
import com.newpathfly.flight.search.webapp.event.SearchResultPollEvent;
import com.newpathfly.flight.search.webapp.registry.SearchResultPollEventRegistry;
import com.newpathfly.model.PollResponse;
import com.newpathfly.model.SearchResultPoll;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.springframework.http.HttpStatus;

public class SearchResultComponent extends VerticalLayout {

    // Logic
    private final transient ShoppingApi _shoppingApi;

    // UI
    private final TripListComponent _tripListComponent;
    private final UI _currentUI;

    public SearchResultComponent(ShoppingApi shoppingApi) {

        _shoppingApi = shoppingApi;

        // constructors
        _tripListComponent = new TripListComponent();
        _currentUI = UI.getCurrent();

        // event listeners
        _currentUI.getSession().getAttribute(SearchResultPollEventRegistry.class).register(e -> {

            SearchResultPoll searchResultPoll = e.getSearchResultPoll();

            _shoppingApi.createPollWithHttpInfo(searchResultPoll).subscribe( //
                    r -> {
                        PollResponse pollResponse = r.getBody();

                        pollResponse.getTrips().forEach(_tripListComponent::add);

                        if (HttpStatus.PARTIAL_CONTENT.equals(r.getStatusCode())) {
                            SearchResultPoll newSearchResultPoll = new SearchResultPoll() //
                                .requestId(searchResultPoll.getRequestId()) //
                                .offset(pollResponse.getTrips().size()); //

                            fire(new SearchResultPollEvent(newSearchResultPoll));
                        }
                    }, //
                    exception -> {
                    } //
            );
        });

        // misc settings

        add( //
                _tripListComponent //
        );
    }

    private void fire(SearchResultPollEvent e) {
        getUI().orElseThrow(
                () -> new RuntimeException("current compoent not attached to a UI - can't fire SearchResponseEvent"))
                .access(() -> {
                    _currentUI.getSession().getAttribute(SearchResultPollEventRegistry.class).sentEvent(e);
                });
    }
}
