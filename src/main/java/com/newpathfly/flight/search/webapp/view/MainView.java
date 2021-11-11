package com.newpathfly.flight.search.webapp.view;

import com.newpathfly.flight.search.webapp.component.SearchComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "")
public class MainView extends VerticalLayout {

    private final SearchComponent _searchComponent;

    public MainView() {

        _searchComponent = new SearchComponent();

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add( //
                _searchComponent //
        );
    }
}
