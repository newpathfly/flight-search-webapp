package com.newpathfly.flight.search.webapp.component;

import com.newpathfly.model.Flight;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class FlightComponent extends VerticalLayout {

    private final Flight _flight;

    public FlightComponent(Flight flight) {
        // initialize
        _flight = flight;

        // construct
        _flight.getSegments().forEach(s -> {
            add(new SegmentComponent(s));
        });
    }

    public Flight getFlight() {
        return _flight;
    }
}
