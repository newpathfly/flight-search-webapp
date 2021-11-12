package com.newpathfly.flight.search.webapp.component;

import java.util.ArrayList;
import java.util.List;

import com.newpathfly.model.Trip;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class TripListComponent extends HorizontalLayout {

    private final List<TripComponent> _tripComponents;

    public TripListComponent() {
        _tripComponents = new ArrayList<>();
    }

    public void add(Trip trip) {
        TripComponent tripComponent = new TripComponent(trip);

        _tripComponents.add(tripComponent);

        getUI().orElseThrow(
                () -> new RuntimeException("current compoent not attached to a UI - can't add tripComponent"))
                .access(() -> add(tripComponent));
    }

    public void clear() {
        remove(_tripComponents.toArray(new TripComponent[0]));
        _tripComponents.clear();
    }
}
