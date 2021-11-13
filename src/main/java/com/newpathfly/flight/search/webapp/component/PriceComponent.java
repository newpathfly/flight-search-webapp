package com.newpathfly.flight.search.webapp.component;

import com.newpathfly.model.Price;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class PriceComponent extends FormLayout {

    private final Price _price;
    private final Binder<Price> _binder;

    private final TextField _totalPriceTextField;

    public PriceComponent(Price price) {
        // initialize
        _price = price;
        _binder = new Binder<>(Price.class);

        // construct
        _totalPriceTextField = new TextField();
        add( //
                _totalPriceTextField //
        );

        // binding
        _binder.forField(_totalPriceTextField) //
                .bind(p -> String.format("%s %.2f", p.getCurrencyCode(), p.getTotalPrice()), null);

        _binder.readBean(price);
    }

    public Price getPrice() {
        return _price;
    }
}
