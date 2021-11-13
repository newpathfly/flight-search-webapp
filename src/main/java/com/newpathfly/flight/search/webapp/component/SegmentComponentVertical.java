package com.newpathfly.flight.search.webapp.component;

import com.newpathfly.model.Segment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;

public class SegmentComponentVertical extends HorizontalLayout {
    
    private final Segment _segment;

    public SegmentComponentVertical(Segment segment) {
        _segment = segment;

        // construct
        add(getTextDiv(""));
        add(VaadinIcon.LINE_V.create());
        add(getTextDiv(segment.getFlightNo()));

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    public Segment getSegment() {
        return _segment;
    }

    private static Div getTextDiv(String text) {
        Div div = new Div();
        div.setText(text);
        div.getStyle().set("padding", "0px");
        div.getStyle().set("margin", "0px");
        div.setWidth("80px");
        return div;
    }
}
