package com.newpathfly.flight.search.webapp.component;

import com.newpathfly.flight.search.webapp.adapter.PriceAdapter;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class PriceComponent extends VerticalLayout {

    public PriceComponent(PriceAdapter price) {

        // construct
        Div priceDiv;
        Div currencyDiv;
        if (null == price) {
            priceDiv = buildTextDiv("N/A", "bold");
            currencyDiv = buildTextDiv("", "light");
        } else {
            priceDiv = buildTextDiv(String.format("%.2f", price.getTotalPrice()), "bold");
            currencyDiv = buildTextDiv(price.getCurrencyCode(), "light");
        }

        add(priceDiv);
        add(currencyDiv);

        setSpacing(false);
        setWidth("100px");
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    private static Div buildTextDiv(String text, String fontWeight) {
        Div div = new Div();
        div.setText(text);
        div.getStyle().set("padding", "0px");
        div.getStyle().set("margin", "0px");
        div.getStyle().set("font-size", "large");
        div.getStyle().set("font-weight", fontWeight);

        div.setHeight("20px");
        return div;
    }
}
