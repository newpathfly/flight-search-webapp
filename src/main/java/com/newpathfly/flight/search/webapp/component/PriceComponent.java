package com.newpathfly.flight.search.webapp.component;

import com.newpathfly.model.Price;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;

public class PriceComponent extends FormLayout {

    private final Price _price;

    public PriceComponent(Price price) {
        // initialize
        _price = price;

        // construct
        Div priceDiv = getTextDiv(String.format("%s %.2f", _price.getCurrencyCode(), _price.getTotalPrice()));
        add(priceDiv);
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
