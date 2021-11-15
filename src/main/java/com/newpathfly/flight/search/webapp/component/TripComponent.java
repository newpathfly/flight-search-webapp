package com.newpathfly.flight.search.webapp.component;

import com.newpathfly.model.Flight;
import com.newpathfly.model.Trip;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.Scroller.ScrollDirection;

public class TripComponent extends HorizontalLayout {

    private final Trip _trip;

    public TripComponent(Trip trip) {

        // initialize
        _trip = trip;

        // construct
        Flight depFlight = _trip.getFlights().get(0);
        add(getFlightTypeIcon(false));
        add(getFlightScroller(depFlight));

        if (_trip.getFlights().size() > 1) {
            // add the return flight
            Flight retFlight = _trip.getFlights().get(1);
            add(getFlightTypeIcon(true));
            add(getFlightScroller(retFlight));
        }

        add(new PriceComponent(_trip.getPrices().getADT()));

        setClassName("trip-component");
        setMargin(true);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    private static Scroller getFlightScroller(Flight flight) {
        FlightComponentHorizontal flightComponentHorizontal = new FlightComponentHorizontal(flight);
        flightComponentHorizontal.getStyle().set("display", "inline-flex");

        Scroller scroller = new Scroller();
        scroller.setScrollDirection(ScrollDirection.HORIZONTAL);
        scroller.setContent(flightComponentHorizontal);
        scroller.setWidth("800px");
        scroller.getStyle().set("overflow-x", "overlay");

        return scroller;
    }

    private static Icon getFlightTypeIcon(boolean returnFlight) {
        Icon icon;
        
        if (returnFlight) {
            icon = VaadinIcon.ARROW_CIRCLE_LEFT.create();
        } else {
            icon = VaadinIcon.ARROW_CIRCLE_RIGHT.create();
        }

        icon.setColor("#AAAAAA");

        return icon;
    }
}
