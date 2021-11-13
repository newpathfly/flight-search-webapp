package com.newpathfly.flight.search.webapp.view;

import com.newpathfly.api.ShoppingApi;
import com.newpathfly.flight.search.webapp.component.LogNotification;
import com.newpathfly.flight.search.webapp.component.SearchButton;
import com.newpathfly.flight.search.webapp.component.SearchRequestComponent;
import com.newpathfly.flight.search.webapp.component.SearchResultComponent;
import com.newpathfly.flight.search.webapp.event.CancelPollingEvent;
import com.newpathfly.flight.search.webapp.event.LogEvent;
import com.newpathfly.flight.search.webapp.event.SearchResultPollEvent;
import com.newpathfly.flight.search.webapp.registry.CancelPollingEventRegistry;
import com.newpathfly.flight.search.webapp.registry.LogEventRegistry;
import com.newpathfly.flight.search.webapp.registry.SearchResultPollEventRegistry;
import com.newpathfly.model.SearchRequest;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import org.springframework.beans.factory.annotation.Autowired;

@Push
@PWA(name = "TicketCombine Flight Search", shortName = "Flight Search", description = "TicketCombine Flight Search with Virtual Interlining technology", enableInstallPrompt = true)
@Route(value = "")
public class MainView extends VerticalLayout {

    // Logic
    private final transient ShoppingApi _shoppingApi;

    // UI
    private final SearchRequestComponent _searchComponent;
    private final SearchButton _searchButton;
    private final SearchResultComponent _searchResultComponent;

    private final UI _currentUI;

    public MainView(@Autowired ShoppingApi shoppingApi) {

        _shoppingApi = shoppingApi;

        // constructors
        _searchComponent = new SearchRequestComponent();
        _searchButton = new SearchButton();
        _searchResultComponent = new SearchResultComponent(shoppingApi);
        _currentUI = UI.getCurrent();

        // UI listeners
        _searchButton.addClickListener(e -> {

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

            _searchResultComponent.clear();

            // obtain the search request
            SearchRequest searchRequest = _searchComponent.getValue();

            // proceed the search and subscribe to the response
            _shoppingApi.createSearch(searchRequest).subscribe( //
                    r -> {
                        fire(new LogEvent(NotificationVariant.LUMO_SUCCESS,
                                String.format("Search request sent successfuly. (RequestId: `%s`, Message: `%s`)",
                                        r.getRequestId(), r.getMessage())));

                        _searchResultComponent.setCurrentRequestId(r.getRequestId());

                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException exception) {
                            fire(new LogEvent(NotificationVariant.LUMO_ERROR, exception.getMessage()));
                            Thread.currentThread().interrupt();
                        }

                        fire(new SearchResultPollEvent());
                    }, //
                    exception -> fire(new LogEvent(NotificationVariant.LUMO_ERROR, exception.getMessage())) //
            );
        });

        // event listeners
        _currentUI.getSession().getAttribute(LogEventRegistry.class).register(e -> {
            _currentUI.access(() -> LogNotification.send(e.getVariant(), e.getMessage()));
        });

        // misc settings
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add( //
                _searchComponent, //
                _searchButton, //
                _searchResultComponent //
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
}
