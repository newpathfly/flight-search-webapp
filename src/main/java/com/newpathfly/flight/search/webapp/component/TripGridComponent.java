package com.newpathfly.flight.search.webapp.component;

import com.newpathfly.model.Trip;
import com.vaadin.flow.component.grid.Grid;

public class TripGridComponent extends Grid<Trip> {

    public TripGridComponent() {
        addComponentColumn(TripComponent::new).setWidth("800px");
        addComponentColumn(t -> new PriceComponent(t.getPrices().getADT()));

        setSelectionMode(SelectionMode.NONE);
        setAllRowsVisible(true);

        getStyle().set("border-style", "none");
    }

    public void refresh() {
        getDataProvider().refreshAll();
    }
}
