package com.newpathfly.flight.search.webapp.component;

import com.newpathfly.api.ShoppingApi;
import com.newpathfly.flight.search.webapp.event.LogEvent;
import com.newpathfly.flight.search.webapp.event.SearchResultPollEvent;
import com.newpathfly.flight.search.webapp.registry.CancelPollingEventRegistry;
import com.newpathfly.flight.search.webapp.registry.LogEventRegistry;
import com.newpathfly.flight.search.webapp.registry.SearchResultPollEventRegistry;
import com.newpathfly.model.PollResponse;
import com.newpathfly.model.SearchResultPoll;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.springframework.http.HttpStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        _currentUI.getSession().getAttribute(CancelPollingEventRegistry.class).register(e -> {
            fire(new LogEvent(NotificationVariant.LUMO_SUCCESS, "Search polling is getting cancelled."));

            // @todo do something here
        });

        _currentUI.getSession().getAttribute(SearchResultPollEventRegistry.class).register(e -> {

            SearchResultPoll searchResultPoll = e.getSearchResultPoll();

            _shoppingApi.createPollWithHttpInfo(searchResultPoll).subscribe( //
                    r -> {
                        PollResponse pollResponse = r.getBody();

                        pollResponse.getTrips().forEach(_tripListComponent::add);

                        if (HttpStatus.PARTIAL_CONTENT.equals(r.getStatusCode())) {
                            int offset = _tripListComponent.getTripComponents().size();

                            fire(new LogEvent(NotificationVariant.LUMO_SUCCESS, String.format(
                                    "Partial content received - continue polling per 3 seconds. (RequestId: `%s`, Offset: `%s`)",
                                    searchResultPoll.getRequestId(), offset)));

                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException exception) {
                                fire(new LogEvent(NotificationVariant.LUMO_ERROR, exception.getMessage()));
                                Thread.currentThread().interrupt();
                            }

                            SearchResultPoll newSearchResultPoll = new SearchResultPoll() //
                                    .requestId(searchResultPoll.getRequestId()) //
                                    .offset(offset); //

                            fire(new SearchResultPollEvent(newSearchResultPoll));
                        } else {
                            fire(new LogEvent(NotificationVariant.LUMO_SUCCESS,
                                    "Full content received - no more polling."));
                        }
                    }, //
                    exception -> {
                        log.error("error when creating poll", exception);
                        fire(new LogEvent(NotificationVariant.LUMO_ERROR, exception.getMessage()));
                    } //
            );
        });

        // misc settings

        add( //
                _tripListComponent //
        );
    }

    public void clear() {
        _tripListComponent.clear();
    }

    private void fire(LogEvent e) {
        getUI().orElseThrow(() -> new RuntimeException("current compoent not attached to a UI - can't fire ErrorEvent"))
                .access(() -> {
                    _currentUI.getSession().getAttribute(LogEventRegistry.class).sentEvent(e);
                });
    }

    private void fire(SearchResultPollEvent e) {
        getUI().orElseThrow(
                () -> new RuntimeException("current compoent not attached to a UI - can't fire SearchResponseEvent"))
                .access(() -> {
                    _currentUI.getSession().getAttribute(SearchResultPollEventRegistry.class).sentEvent(e);
                });
    }
}