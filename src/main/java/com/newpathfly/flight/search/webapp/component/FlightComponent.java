package com.newpathfly.flight.search.webapp.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.newpathfly.flight.search.webapp.model.Stop;
import com.newpathfly.model.Flight;
import com.newpathfly.model.Segment;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class FlightComponent extends VerticalLayout {


    private final Flight _flight;

    public FlightComponent(Flight flight) {
        // initialize
        _flight = flight;

        // construct

        add(VaadinIcon.FLIGHT_TAKEOFF.create());
        
        List<Stop> stops = buildStops();

        for (int i = 0; i < stops.size() - 1; i++) {
            add(new StopComponent(stops.get(i)));
            add(VaadinIcon.LINE_V.create());
        }

        add(new StopComponent(stops.get(stops.size() - 1)));

        add(VaadinIcon.FLIGHT_LANDING.create());


        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setSpacing(false);
    }

    public Flight getFlight() {
        return _flight;
    }

    private List<Stop> buildStops() {

        List<Stop> stops = new ArrayList<>();

        Iterator<Segment> segmentIterator = _flight.getSegments().iterator();

        Segment lastSegment = null;

        while(segmentIterator.hasNext()) {
            Segment nextSegment = segmentIterator.next();

            stops.add(new Stop(lastSegment, nextSegment));

            lastSegment = nextSegment;
        }

        stops.add(new Stop(lastSegment, null));

        return stops;
    }
}
