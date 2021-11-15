package com.newpathfly.flight.search.webapp.component;

import java.util.Locale;

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
        Div currencyDiv;
        if (null == _price) {
            priceDiv = getTextDiv("N/A", "bold");
            currencyDiv = getTextDiv("", "light");
        } else {
            priceDiv = getTextDiv(String.format(Locale.ENGLISH, "%.2f", _price.getTotalPrice()), "bold");
            currencyDiv = getTextDiv(_price.getCurrencyCode(), "light");
        }

        add(priceDiv);
        add(currencyDiv);

        setSpacing(false);
        setWidth("100px");
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    public Price getPrice() {
        return _price;
    }

    private static Div getTextDiv(String text, String fontWeight) {
        Div div = new Div();
        div.setText(text);
        div.getStyle().set("padding", "0px");
        div.getStyle().set("margin", "0px");
        div.getStyle().set("text-align", "center");
        div.getStyle().set("justify-content", "center");
        div.getStyle().set("font-size", "large");
        div.getStyle().set("font-weight", fontWeight);

        div.setHeight("20px");
        return div;
    }
}
