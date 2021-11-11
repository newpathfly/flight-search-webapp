package com.newpathfly.flight.search.webapp.view;

import com.newpathfly.flight.search.webapp.component.SearchButton;
import com.newpathfly.flight.search.webapp.component.SearchComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "")
public class MainView extends VerticalLayout {

    private final SearchComponent _searchComponent;
    private final SearchButton _searchButton;

    public MainView() {

        _searchComponent = new SearchComponent();
        _searchButton = new SearchButton();

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add( //
                _searchComponent, //
                _searchButton
        );
    }
}
