package com.newpathfly.flight.search.webapp.component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.newpathfly.flight.search.webapp.model.Stop;
import com.newpathfly.flight.search.webapp.util.DateUtils;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class StopComponentVertical extends VerticalLayout {
    private final transient Stop _stop;

    public StopComponentVertical(Stop stop) {
        _stop = stop;

        String arrDateTime = null;
        String airport = null;
        String depDateTime = null;

        if (null != _stop.getInboundSegment()) {
            arrDateTime = DateUtils.getLocalDateTime(_stop.getInboundSegment().getArrDate(), _stop.getInboundSegment().getArrTime())
                    .format(DateUtils.OUTPUT_DATETIME_FORMATTER);

            airport = _stop.getInboundSegment().getArrAirport();
        }

        if (null != _stop.getOutboundSegment()) {
            airport = _stop.getOutboundSegment().getDepAirport();

            depDateTime = DateUtils.getLocalDateTime(_stop.getOutboundSegment().getDepDate(), _stop.getOutboundSegment().getDepTime())
                    .format(DateUtils.OUTPUT_DATETIME_FORMATTER);
        }

        // construct
        if (null != arrDateTime)
            add(getTextDiv(arrDateTime, "lighter"));

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(getTextDiv(""), getTextDiv(airport, "bold"), getTextDiv(getDuration()));
        horizontalLayout.setAlignItems(Alignment.CENTER);
        horizontalLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        add(horizontalLayout);

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

    public String getDuration() {
        if (null == _stop.getInboundSegment())
            return "";

        if (null == _stop.getOutboundSegment())
            return "";

        LocalDateTime dt1 = DateUtils.getLocalDateTime(_stop.getInboundSegment().getArrDate(), _stop.getInboundSegment().getArrTime());
        LocalDateTime dt2 = DateUtils.getLocalDateTime(_stop.getOutboundSegment().getDepDate(), _stop.getOutboundSegment().getDepTime());

        return String.format("%shrs", dt1.until(dt2, ChronoUnit.HOURS));
    }

    private static Div getTextDiv(String text, String fontWeight) {
        Div div = new Div();
        div.setText(text);
        div.getStyle().set("padding", "0px");
        div.getStyle().set("margin", "0px");
        div.getStyle().set("font-weight", fontWeight);
        return div;
    }

    private static Div getTextDiv(String text) {
        Div div = new Div();
        div.setText(text);
        div.getStyle().set("padding", "0px");
        div.getStyle().set("margin", "0px");
        div.getStyle().set("text-align", "center");
        div.getStyle().set("justify-content", "center");
        div.getStyle().set("font-size", "small");
        div.setWidth("50px");
        return div;
    }
}
