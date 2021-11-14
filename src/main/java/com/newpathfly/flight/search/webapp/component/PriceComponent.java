package com.newpathfly.flight.search.webapp.component;

import com.newpathfly.model.Price;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class PriceComponent extends VerticalLayout {

    private final Price _price;

    public PriceComponent(Price price) {
        // initialize
        _price = price;

        // construct
        Div priceDiv;
        if(null == _price) {
            priceDiv = getTextDiv("ERROR");
        } else {
            priceDiv = getTextDiv(String.format("%.2f %s", _price.getTotalPrice(), _price.getCurrencyCode()));
        }

        add(priceDiv);
        setWidth("100px");
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    public Price getPrice() {
        return _price;
    }

    private static Div getTextDiv(String text) {
        Div div = new Div();
        div.setText(text);
        div.getStyle().set("padding", "0px");
        div.getStyle().set("margin", "0px");
        div.getStyle().set("text-align", "center");
        div.getStyle().set("justify-content", "center");
        div.getStyle().set("font-size", "x-large");
        div.setHeight("20px");
        return div;
    }
}
