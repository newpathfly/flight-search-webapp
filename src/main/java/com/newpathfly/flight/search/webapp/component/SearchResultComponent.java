package com.newpathfly.flight.search.webapp.component;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class SearchResultComponent extends VerticalLayout {

    // UI
    private final SortControlComponent _sortControlComponent;
    private final TripListComponent _tripListComponent;

    public SearchResultComponent() {

        // constructors
        _sortControlComponent = new SortControlComponent();
        _tripListComponent = new TripListComponent();

        // misc settings

        add( //
                _sortControlComponent, //
                _tripListComponent //
        );

        setSpacing(false);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
    }

    public TripListComponent getTripListComponent() {
        return _tripListComponent;
    }

    public void clear() {
        _tripListComponent.clear();
    }
}
