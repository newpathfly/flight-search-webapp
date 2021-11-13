package com.newpathfly.flight.search.webapp.component;

import com.newpathfly.model.Flight;
import com.newpathfly.model.Trip;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.Scroller.ScrollDirection;

public class TripComponentHorizontal extends HorizontalLayout {

    private final Trip _trip;

    public TripComponentHorizontal(Trip trip) {

        // initialize
        _trip = trip;

        // construct
        Flight depFlight = _trip.getFlights().get(0);
        add(getFlightScroller(depFlight, VaadinIcon.ARROW_CIRCLE_RIGHT));

        if (_trip.getFlights().size() > 1) {
            Flight retFlight = _trip.getFlights().get(1);
            add(getFlightScroller(retFlight, VaadinIcon.ARROW_CIRCLE_LEFT));
        }

        add(new PriceComponent(_trip.getPrices().getADT()));

        setClassName("trip-component");
        getStyle().set("border-width", "1px");
        getStyle().set("border-color", "#AAAAAA");
        getStyle().set("border-top-style", "dotted");
        getStyle().set("border-bottom-style", "dotted");

        setMargin(true);
    }

    private static Scroller getFlightScroller(Flight flight, VaadinIcon icon) {
        FlightComponentHorizontal flightComponentHorizontal = new FlightComponentHorizontal(flight, icon);
        flightComponentHorizontal.getStyle().set("display", "inline-flex");

        Scroller scroller = new Scroller();
        scroller.setScrollDirection(ScrollDirection.HORIZONTAL);
        scroller.setContent(flightComponentHorizontal);
        scroller.setWidth("720px");

        return scroller;
    }
}
