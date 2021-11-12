package com.newpathfly.flight.search.webapp.component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import com.newpathfly.flight.search.webapp.model.Stop;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class StopComponent extends VerticalLayout {

    private static final DateTimeFormatter INPUT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("uuuuMMdd HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);

    private static final DateTimeFormatter OUTPUT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/uuuu HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);

    private final transient Stop _stop;

    public StopComponent(Stop stop) {
        _stop = stop;

        String arrDateTime = null;
        String airport = null;
        String depDateTime = null;

        if (null != _stop.getInboundSegment()) {
            arrDateTime = getLocalDateTime(_stop.getInboundSegment().getArrDate(), _stop.getInboundSegment().getArrTime())
                    .format(OUTPUT_DATETIME_FORMATTER);

            airport = _stop.getInboundSegment().getArrAirport();
        }

        if (null != _stop.getOutboundSegment()) {
            airport = _stop.getOutboundSegment().getDepAirport();

            depDateTime = getLocalDateTime(_stop.getOutboundSegment().getDepDate(), _stop.getOutboundSegment().getDepTime())
                    .format(OUTPUT_DATETIME_FORMATTER);
        }

        // construct
        if (null != arrDateTime)
            add(getTextDiv(arrDateTime, "lighter"));

        add(getTextDiv(airport, "bolder"));

        if (null != depDateTime)
            add(getTextDiv(depDateTime, "lighter"));

        // settings
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setSpacing(false);
    }

    public Stop getStop() {
        return _stop;
    }

    private static LocalDateTime getLocalDateTime(String date, String time) {
        return LocalDateTime.parse(date + " " + time, INPUT_DATETIME_FORMATTER);
    }

    private static Div getTextDiv(String text, String fontWeight) {
        Div div = new Div();
        div.setText(text);
        div.getStyle().set("padding", "0px");
        div.getStyle().set("margin", "0px");
        div.getStyle().set("font-weight", fontWeight);
        return div;
    }
}
