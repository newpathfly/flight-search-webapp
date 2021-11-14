package com.newpathfly.flight.search.webapp.component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.newpathfly.flight.search.webapp.model.Stop;
import com.newpathfly.flight.search.webapp.util.DateUtils;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class StopComponentHorizontal extends HorizontalLayout {
    private final transient Stop _stop;

    public StopComponentHorizontal(Stop stop) {
        _stop = stop;

        LocalDateTime arrDateTime = null;
        String airport = null;
        LocalDateTime depDateTime = null;

        if (null != _stop.getInboundSegment()) {
            arrDateTime = DateUtils.getLocalDateTime(_stop.getInboundSegment().getArrDate(),
                    _stop.getInboundSegment().getArrTime());

            airport = _stop.getInboundSegment().getArrAirport();
        }

        if (null != _stop.getOutboundSegment()) {
            airport = _stop.getOutboundSegment().getDepAirport();

            depDateTime = DateUtils.getLocalDateTime(_stop.getOutboundSegment().getDepDate(),
                    _stop.getOutboundSegment().getDepTime());
        }

        // construct
        if (null != arrDateTime) {
            add(getDateTimeLayout(arrDateTime));
        }

        add(getStopLayout(airport));

        if (null != depDateTime) {
            add(getDateTimeLayout(depDateTime));

        }

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

        LocalDateTime dt1 = DateUtils.getLocalDateTime(_stop.getInboundSegment().getArrDate(),
                _stop.getInboundSegment().getArrTime());
        LocalDateTime dt2 = DateUtils.getLocalDateTime(_stop.getOutboundSegment().getDepDate(),
                _stop.getOutboundSegment().getDepTime());

        return String.format("%shrs", dt1.until(dt2, ChronoUnit.HOURS));
    }

    private VerticalLayout getStopLayout(String airport) {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(getTextDiv(""), getTextDiv(airport, "bold"), getTextDiv(getDuration()));
        verticalLayout.setAlignItems(Alignment.CENTER);
        verticalLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        verticalLayout.setClassName("stop-layout");
        verticalLayout.getStyle().remove("width");
        verticalLayout.setPadding(false);
        verticalLayout.setMargin(false);
        return verticalLayout;
    }

    private VerticalLayout getDateTimeLayout(LocalDateTime dateTime) {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(getTextDiv(dateTime.format(DateUtils.OUTPUT_DATE_FORMATTER)));
        verticalLayout.add(getTextDiv(dateTime.format(DateUtils.OUTPUT_WEEKDAY_FORMATTER)));
        verticalLayout.add(getTextDiv(dateTime.format(DateUtils.OUTPUT_TIME_FORMATTER)));
        verticalLayout.setAlignItems(Alignment.CENTER);
        verticalLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        verticalLayout.setMargin(false);

        add(verticalLayout);

        return verticalLayout;
    }

    private static Div getTextDiv(String text, String fontWeight) {
        Div div = new Div();
        div.setText(text);
        div.getStyle().set("padding", "0px");
        div.getStyle().set("margin", "0px");
        div.getStyle().set("font-weight", fontWeight);
        div.getStyle().set("font-size", "large");
        div.getStyle().set("text-align", "center");
        div.getStyle().set("justify-content", "center");
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
        div.setHeight("20px");
        return div;
    }
}