package com.newpathfly.flight.search.webapp.view;

import com.newpathfly.api.ShoppingApi;
import com.newpathfly.flight.search.webapp.component.SearchButton;
import com.newpathfly.flight.search.webapp.component.SearchComponent;
import com.newpathfly.flight.search.webapp.event.CancelPollingEvent;
import com.newpathfly.flight.search.webapp.event.ErrorEvent;
import com.newpathfly.flight.search.webapp.event.SearchResponseEvent;
import com.newpathfly.flight.search.webapp.registry.CancelPollingEventRegistry;
import com.newpathfly.flight.search.webapp.registry.ErrorEventRegistry;
import com.newpathfly.flight.search.webapp.registry.SearchResponseEventRegistry;
import com.newpathfly.model.SearchRequest;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

@Push
@Route(value = "")
public class MainView extends VerticalLayout {

    // Logic
    private final transient ShoppingApi _shoppingApi;

    // UI
    private final SearchComponent _searchComponent;
    private final SearchButton _searchButton;
    private final UI _currentUI;

    public MainView(@Autowired ShoppingApi shoppingApi) {

        _shoppingApi = shoppingApi;

        // constructors
        _searchComponent = new SearchComponent();
        _searchButton = new SearchButton();
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

            // obtain the search request
            SearchRequest searchRequest = _searchComponent.getValue();

            // proceed the search and subscribe to the response
            _shoppingApi.createSearch(searchRequest).subscribe( //
                    r -> fire(new SearchResponseEvent(r)), //
                    exception -> fire(new ErrorEvent(exception.getMessage())), //
                    () -> {
                        // thing to do upon completion
                    } //
            );
        });

        // event listeners
        _currentUI.getSession().getAttribute(ErrorEventRegistry.class).register(e -> {
            _currentUI.access(() -> {
                Notification notification = new Notification(e.getMessage());
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setDuration(10000);
                notification.setPosition(Position.MIDDLE);
                notification.open();
            });
        });

        // misc settings
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add( //
                _searchComponent, //
                _searchButton);
    }

    private void fire(ErrorEvent e) {
        getUI().orElseThrow(() -> new RuntimeException("current compoent not attached to a UI - can't fire ErrorEvent"))
                .access(() -> {
                    _currentUI.getSession().getAttribute(ErrorEventRegistry.class).sentEvent(e);
                });
    }

    private void fire(SearchResponseEvent e) {
        getUI().orElseThrow(
                () -> new RuntimeException("current compoent not attached to a UI - can't fire SearchResponseEvent"))
                .access(() -> {
                    _currentUI.getSession().getAttribute(SearchResponseEventRegistry.class).sentEvent(e);
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
