package com.newpathfly.flight.search.webapp.component;

import java.util.ArrayList;
import java.util.List;

import com.newpathfly.model.Trip;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class TripListComponent extends VerticalLayout {

    private final List<TripComponentHorizontal> _tripComponents;

    private final Div _noResultDiv;

    public TripListComponent() {
        _noResultDiv = new Div();
        _noResultDiv.setText("No results at the moment");

        _tripComponents = new ArrayList<>();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setSpacing(false);
        setWidth("956px");

        getStyle().set("border-width", "1px");
        getStyle().set("border-color", "#AAAAAA");
        getStyle().set("border-bottom-style", "dotted");

        add(_noResultDiv);
    }

    public void add(Trip trip) {
        TripComponentHorizontal tripComponent = new TripComponentHorizontal(trip);

        _tripComponents.add(tripComponent);

        getUI().orElseThrow(
                () -> new RuntimeException("current component not attached to a UI - can't add tripComponent"))
                .access(() -> {
                    _noResultDiv.setVisible(false);
                    add(tripComponent);
                });
    }

    public void clear() {
        remove(_tripComponents.toArray(new TripComponentHorizontal[0]));
        _tripComponents.clear();

        _noResultDiv.setVisible(true);
    }

    public List<TripComponentHorizontal> getTripComponents() {
        return _tripComponents;
    }
}
