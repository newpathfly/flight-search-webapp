package com.newpathfly.flight.search.webapp.component;

import java.util.ArrayList;
import java.util.List;

import com.newpathfly.model.Trip;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class TripListComponent extends VerticalLayout {

    private final List<TripComponentHorizontal> _tripComponents;

    public TripListComponent() {
        _tripComponents = new ArrayList<>();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setSpacing(false);
    }

    public void add(Trip trip) {
        TripComponentHorizontal tripComponent = new TripComponentHorizontal(trip);

        _tripComponents.add(tripComponent);

        getUI().orElseThrow(
                () -> new RuntimeException("current component not attached to a UI - can't add tripComponent"))
                .access(() -> add(tripComponent));
    }

    public void clear() {
        remove(_tripComponents.toArray(new TripComponentHorizontal[0]));
        _tripComponents.clear();
    }

    public List<TripComponentHorizontal> getTripComponents() {
        return _tripComponents;
    }
}
