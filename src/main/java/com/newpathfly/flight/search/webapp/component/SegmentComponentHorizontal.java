package com.newpathfly.flight.search.webapp.component;

import com.newpathfly.model.Segment;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class SegmentComponentHorizontal extends VerticalLayout {
    private final Segment _segment;

    public SegmentComponentHorizontal(Segment segment) {
        _segment = segment;

        // construct
        add(getTextDiv(""));
        add(VaadinIcon.LINE_H.create());
        add(getTextDiv(segment.getFlightNo()));

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setPadding(false);
        setWidth("60px");
    }

    public Segment getSegment() {
        return _segment;
    }

    private static Div getTextDiv(String text) {
        Div div = new Div();
        div.setText(text);
        div.getStyle().set("padding", "0px");
        div.getStyle().set("margin", "0px");
        div.getStyle().set("text-align", "center");
        div.getStyle().set("justify-content", "center");
        return div;
    }
}
