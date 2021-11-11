package com.newpathfly.flight.search.webapp.view;

import com.newpathfly.api.ShoppingApi;
import com.newpathfly.flight.search.webapp.component.SearchButton;
import com.newpathfly.flight.search.webapp.component.SearchComponent;
import com.newpathfly.flight.search.webapp.event.CancelPollingEvent;
import com.newpathfly.flight.search.webapp.event.ErrorEvent;
import com.newpathfly.flight.search.webapp.event.SearchResponseEvent;
import com.newpathfly.model.SearchRequest;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@Route(value = "")
public class MainView extends VerticalLayout {

    // Logic
    private final transient ShoppingApi _shoppingApi;
    private final transient ApplicationEventPublisher _applicationEventPublisher;

    // UI
    private final SearchComponent _searchComponent;
    private final SearchButton _searchButton;

    @Autowired
    public MainView(ShoppingApi shoppingApi, ApplicationEventPublisher applicationEventPublisher) {

        _shoppingApi = shoppingApi;
        _applicationEventPublisher = applicationEventPublisher;

        _searchComponent = new SearchComponent();
        _searchButton = new SearchButton();

        _searchButton.addClickListener(e -> {

            // cancel any existing polling
            _applicationEventPublisher.publishEvent(new CancelPollingEvent(null));

            // obtain the search request
            SearchRequest searchRequest = _searchComponent.getValue();

            // proceed the search and subscribe to the response
            _shoppingApi.createSearch(searchRequest).subscribe( //
                    r -> _applicationEventPublisher.publishEvent(new SearchResponseEvent(searchRequest)), //
                    exception -> applicationEventPublisher.publishEvent(new ErrorEvent(exception.getMessage())), //
                    () -> {
                        // thing to do upon completion
                    } //
            );
        });

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add( //
                _searchComponent, //
                _searchButton);
    }
}
