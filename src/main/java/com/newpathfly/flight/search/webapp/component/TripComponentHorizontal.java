package com.newpathfly.flight.search.webapp.component;

import com.newpathfly.model.Trip;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.Scroller.ScrollDirection;

public class TripComponentHorizontal extends HorizontalLayout {

    private final Trip _trip;

    public TripComponentHorizontal(Trip trip) {

        // initialize
        _trip = trip;

        // construct
        _trip.getFlights().forEach(f -> {
            FlightComponentHorizontal flightComponentHorizontal = new FlightComponentHorizontal(f);
            flightComponentHorizontal.getStyle().set("display", "inline-flex");

            Scroller scroller = new Scroller();
            scroller.setScrollDirection(ScrollDirection.HORIZONTAL);
            scroller.setContent(flightComponentHorizontal);
            scroller.setWidth("720px");

            add(scroller);
        });
        add(new PriceComponent(_trip.getPrices().getADT()));
    }
}
