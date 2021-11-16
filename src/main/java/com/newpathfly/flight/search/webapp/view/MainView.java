package com.newpathfly.flight.search.webapp.view;

import com.newpathfly.api.ShoppingApi;
import com.newpathfly.flight.search.webapp.component.LogNotification;
import com.newpathfly.flight.search.webapp.component.SearchButton;
import com.newpathfly.flight.search.webapp.component.SearchRequestCustomField;
import com.newpathfly.flight.search.webapp.component.SearchResultGridComponent;
import com.newpathfly.flight.search.webapp.event.CancelPollingEvent;
import com.newpathfly.flight.search.webapp.event.LogEvent;
import com.newpathfly.flight.search.webapp.event.SearchResultPollEvent;
import com.newpathfly.flight.search.webapp.registry.CancelPollingEventRegistry;
import com.newpathfly.flight.search.webapp.registry.LogEventRegistry;
import com.newpathfly.flight.search.webapp.registry.SearchResultPollEventRegistry;
import com.newpathfly.model.PollResponse;
import com.newpathfly.model.SearchRequest;
import com.newpathfly.model.SearchResultPoll;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;

@Push
@PWA(name = "TicketCombine Flight Search", shortName = "Flight Search", description = "TicketCombine Flight Search with Virtual Interlining technology", enableInstallPrompt = false)
@Route(value = "")
@Slf4j
public class MainView extends VerticalLayout {
    private static final int MAX_POLL_OFFSET = 100;

    private String _currentRequestId;

    // Logic
    private final transient ShoppingApi _shoppingApi;

    // UI
    private final SearchRequestCustomField _searchComponent;
    private final SearchButton _searchButton;
    private final SearchResultGridComponent _searchResultGridComponent;

    private final UI _currentUI;

    public MainView(@Autowired ShoppingApi shoppingApi) {

        _shoppingApi = shoppingApi;

        // constructors
        _searchComponent = new SearchRequestCustomField();
        _searchButton = new SearchButton();
        _searchResultGridComponent = new SearchResultGridComponent();
        _currentUI = UI.getCurrent();

        // UI Listeners
        _searchButton.addClickListener(e -> searchButtonClickListener());

        // Event Listeners

        // log event for showing message as notifications in the UI
        _currentUI.getSession().getAttribute(LogEventRegistry.class).register(e -> {
            _currentUI.access(() -> LogNotification.send(e.getVariant(), e.getMessage()));
        });

        // cancellation event for cancelling the polling
        _currentUI.getSession().getAttribute(CancelPollingEventRegistry.class).register(e -> {
            fire(new LogEvent(NotificationVariant.LUMO_SUCCESS, "Search polling is getting cancelled."));
            _currentRequestId = null;
        });

        // search result polling event for polling the search result from API
        _currentUI.getSession().getAttribute(SearchResultPollEventRegistry.class).register(e -> {

            if (null == _currentRequestId || _currentRequestId.isEmpty()) {
                return;
            }

            SearchResultPoll searchResultPoll = new SearchResultPoll() //
                    .requestId(_currentRequestId) //
                    .offset(_searchResultGridComponent.size()); //

            _shoppingApi.createPollWithHttpInfo(searchResultPoll).subscribe( //
                    this::handlePollResponseEntity, //
                    exception -> {
                        log.error("error when handling poll response.", exception);
                        fire(new LogEvent(NotificationVariant.LUMO_ERROR, exception.getMessage()));
                    } //
            );
        });

        // misc settings
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSpacing(false);

        add( //
                _searchComponent, //
                _searchButton, //
                _searchResultGridComponent //
        );
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

    private void fire(CancelPollingEvent e) {
        getUI().orElseThrow(
                () -> new RuntimeException("current compoent not attached to a UI - can't fire CancelPollingEvent"))
                .access(() -> {
                    _currentUI.getSession().getAttribute(CancelPollingEventRegistry.class).sentEvent(e);
                });
    }

    private void searchButtonClickListener() {

        // validation
        if (_searchComponent.getDepAirportTextField().getValue().isEmpty()) {
            _searchComponent.getDepAirportTextField().focus();
            return;
        }

        if (_searchComponent.getArrAirportTextField().getValue().isEmpty()) {
            _searchComponent.getArrAirportTextField().focus();
            return;
        }

        if (_searchComponent.getDepDatePicker().getValue() == null) {
            _searchComponent.getDepDatePicker().focus();
            return;
        }

        if (_searchComponent.getRetDatePicker().getValue() == null) {
            _searchComponent.getRetDatePicker().focus();
            return;
        }

        // cancel any existing polling
        fire(new CancelPollingEvent());

        _searchResultGridComponent.clear();

        // obtain the search request
        SearchRequest searchRequest = _searchComponent.getValue();

        // proceed the search and subscribe to the response
        _shoppingApi.createSearch(searchRequest).subscribe( //
                r -> {
                    fire(new LogEvent(NotificationVariant.LUMO_SUCCESS,
                            String.format("Search request sent successfuly. (RequestId: `%s`, Message: `%s`)",
                                    r.getRequestId(), r.getMessage())));

                    _currentRequestId = r.getRequestId();

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException exception) {
                        fire(new LogEvent(NotificationVariant.LUMO_ERROR, exception.getMessage()));
                        Thread.currentThread().interrupt();
                    }

                    fire(new SearchResultPollEvent());
                }, //
                exception -> {
                    log.error("error when handling search response.", exception);
                    fire(new LogEvent(NotificationVariant.LUMO_ERROR, exception.getMessage()));
                } //
        );
    }

    private void handlePollResponseEntity(ResponseEntity<PollResponse> r) {
        PollResponse pollResponse = r.getBody();

        if (null != pollResponse && !pollResponse.getTrips().isEmpty()) {
            getUI().orElseThrow(
                    () -> new RuntimeException("current compoent not attached to a UI - cannot add trips to grid"))
                    .access(() -> {
                        _searchResultGridComponent.add(pollResponse.getTrips());
                    });
        }

        if (HttpStatus.PARTIAL_CONTENT.equals(r.getStatusCode())) {
            int offset = _searchResultGridComponent.size();

            if (offset >= MAX_POLL_OFFSET) {
                return;
            }

            fire(new LogEvent(NotificationVariant.LUMO_SUCCESS, String.format(
                    "Partial content received - continue polling per 5 seconds. (RequestId: `%s`, Offset: `%s`)",
                    _currentRequestId, offset)));

            try {
                Thread.sleep(5000);
            } catch (InterruptedException exception) {
                fire(new LogEvent(NotificationVariant.LUMO_ERROR, exception.getMessage()));
                Thread.currentThread().interrupt();
            }

            fire(new SearchResultPollEvent());
        } else {
            fire(new LogEvent(NotificationVariant.LUMO_SUCCESS, "Full content received - no more polling."));
        }
    }
}
