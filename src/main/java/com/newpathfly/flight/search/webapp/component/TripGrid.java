package com.newpathfly.flight.search.webapp.component;

import com.newpathfly.flight.search.webapp.adapter.PriceAdapter;
import com.newpathfly.flight.search.webapp.adapter.TripAdapter;
import com.newpathfly.model.Price;
import com.vaadin.flow.component.grid.Grid;

public class TripGrid extends Grid<TripAdapter> {

    public TripGrid() {
        addComponentColumn(t -> new TripComponent(new TripAdapter(t))).setWidth("800px");

        addComponentColumn(t -> {
            Price price = t.getPrices().getALL();
            return new PriceComponent(null == price ? null : new PriceAdapter(price));
        });

        setSelectionMode(SelectionMode.NONE);
        setPageSize(10);

        getStyle().set("border-style", "none");
    }

    public void refresh() {
        getDataProvider().refreshAll();
    }
}
