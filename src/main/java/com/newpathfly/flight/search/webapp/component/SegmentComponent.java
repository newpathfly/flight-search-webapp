package com.newpathfly.flight.search.webapp.component;

import com.newpathfly.model.Segment;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class SegmentComponent extends VerticalLayout {

    public SegmentComponent(Segment segment) {

        // construct
        add(buildCarrierLogo(segment.getCarrier()));
        add(buildFlightAnchor(segment.getFlightNo()));

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setPadding(false);
        setWidth("60px");
        setSpacing(false);
    }

    private static Image buildCarrierLogo(String carrierCode) {
        Image image = new Image(
                String.format("airline_logos/%s.png", carrierCode.toLowerCase()),
                carrierCode);

        image.setWidth("26px");
        image.setHeight("26px");

        return image;
    }

    private static Anchor buildFlightAnchor(String flightNo) {
        String carrierCode = flightNo.substring(0, 2);
        String numberOnly = flightNo.substring(2);

        Anchor anchor = new Anchor(
                String.format("https://www.flightstats.com/v2/flight-ontime-performance-rating/%s/%s", carrierCode,
                        numberOnly),
                flightNo);

        anchor.setTarget("_blank");
        anchor.getStyle().set("padding", "0px");
        anchor.getStyle().set("margin", "0px");
        anchor.getStyle().set("font-weight", "bold");
        anchor.getStyle().set("text-decoration", "none");

        return anchor;
    }
}
