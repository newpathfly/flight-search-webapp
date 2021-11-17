package com.newpathfly.flight.search.webapp.component;

import com.newpathfly.model.Trip;
import com.vaadin.flow.component.grid.Grid;

public class TripGrid extends Grid<Trip> {

    public TripGrid() {
        addComponentColumn(TripComponent::new).setWidth("800px").setKey("trip");
        addComponentColumn(t -> new PriceComponent(t.getPrices().getALL())).setKey("price");

        setSelectionMode(SelectionMode.NONE);
        setHeight("auto !important");

        getStyle().set("border-style", "none");
    }

    public void refresh() {
        getDataProvider().refreshAll();
    }
}
