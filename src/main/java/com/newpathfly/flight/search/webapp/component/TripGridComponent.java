package com.newpathfly.flight.search.webapp.component;

import java.util.List;

import com.newpathfly.model.Trip;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.DataProvider;

public class TripGridComponent extends Grid<Trip> {

    private final DataProvider<Trip, ?> _dataProvider;

    public TripGridComponent(List<Trip> trips) {

        setItems(trips);

        _dataProvider = getDataProvider();

        addComponentColumn(TripComponent::new).setWidth("800px");
        addComponentColumn(t -> new PriceComponent(t.getPrices().getADT()));

        setSelectionMode(SelectionMode.NONE);
        setAllRowsVisible(true);

        getStyle().set("border-style", "none");
    }

    public void refresh() {
        _dataProvider.refreshAll();
    }
}
