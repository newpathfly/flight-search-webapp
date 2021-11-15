package com.newpathfly.flight.search.webapp.component;

import java.util.ArrayList;
import java.util.List;

import com.newpathfly.model.Trip;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.DataProvider;

public class TripGridComponent extends Grid<Trip> {

    private final List<Trip> _trips;
    private final DataProvider<Trip, ?> _dataProvider;

    public TripGridComponent() {

        _trips = new ArrayList<>();

        setItems(_trips);

        _dataProvider = getDataProvider();

        addComponentColumn(TripComponent::new);
        addComponentColumn(t -> new PriceComponent(t.getPrices().getALL()));

        setSelectionMode(SelectionMode.NONE);
        setWidth("956px");
    }

    public void add(Trip trip) {
        _trips.add(trip);
        _dataProvider.refreshAll();
    }

    public void clear() {
        _trips.clear();
        _dataProvider.refreshAll();
    }
    
    public List<Trip> getTrips() {
        return _trips;
    }
}
