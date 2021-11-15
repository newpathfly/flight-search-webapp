package com.newpathfly.flight.search.webapp.component;

import com.newpathfly.model.Trip;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class TripComponentVertical extends HorizontalLayout {

    private final Trip _trip;

    public TripComponentVertical(Trip trip) {

        // initialize
        _trip = trip;

        // construct
        _trip.getFlights().forEach(f -> {        
            add(new FlightComponentVertical(f));
        });
        add(new PriceComponent(_trip.getPrices().getADT()));
        setSpacing(false);
    }
}
