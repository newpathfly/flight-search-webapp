package com.newpathfly.flight.search.webapp.component;

import com.newpathfly.model.Trip;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class TripComponent extends VerticalLayout {
    
    private final Trip _trip;

    public TripComponent(Trip trip) {

        // initialize
        _trip = trip;

        // construct
        _trip.getFlights().forEach(f -> add(new FlightComponent(f)));
        add(new PriceComponent(_trip.getPrices().getADT()));
    }
}
