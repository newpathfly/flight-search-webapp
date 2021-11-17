package com.newpathfly.flight.search.webapp.component;

import com.newpathfly.flight.search.webapp.adapter.TripAdapter;
import com.newpathfly.model.Flight;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.Scroller.ScrollDirection;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class TripComponent extends VerticalLayout {

    public TripComponent(TripAdapter trip) {

        // construct
        Flight depFlight = trip.getFlights().get(0);
        add(getFlightLayout(depFlight, false));

        if (trip.getFlights().size() > 1) {
            // add the return flight
            Flight retFlight = trip.getFlights().get(1);
            add(getFlightLayout(retFlight, true));
        }

        setMargin(true);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    private static HorizontalLayout getFlightLayout(Flight flight, boolean returnFlight) {
        HorizontalLayout flightLayout = new HorizontalLayout();
        flightLayout.add(getFlightTypeIcon(returnFlight));
        flightLayout.add(getFlightScroller(flight));

        flightLayout.setAlignItems(Alignment.CENTER);
        flightLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        return flightLayout;
    }

    private static Scroller getFlightScroller(Flight flight) {
        FlightComponent flightComponentHorizontal = new FlightComponent(flight);
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
