package com.newpathfly.flight.search.webapp.component;

import java.util.ArrayList;
import java.util.List;

import com.newpathfly.model.Trip;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;

public class SearchResultGridComponent extends VerticalLayout {

    private final List<Trip> _trips;

    // UI
    private final RadioButtonGroup<String> _sortControl;
    private final TripGridComponent _tripGridComponent;

    public SearchResultGridComponent() {

        _trips = new ArrayList<>();

        // constructors
        _sortControl = getSortControl();
        HorizontalLayout sortControlLayout = new HorizontalLayout(_sortControl);
        sortControlLayout.setJustifyContentMode(JustifyContentMode.END);
        sortControlLayout.setWidthFull();

        _tripGridComponent = new TripGridComponent(_trips);

        // misc settings

        add( //
                sortControlLayout, //
                _tripGridComponent //
        );

        setSpacing(false);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setWidth("956px");

        getStyle().set("border-width", "1px");
        getStyle().set("border-color", "#AAAAAA");
        getStyle().set("border-top-style", "dotted");
    }

    public TripGridComponent getTripGridComponent() {
        return _tripGridComponent;
    }

    public void add(Trip trip) {
        _trips.add(trip);
        _tripGridComponent.refresh();
    }

    public void clear() {
        _trips.clear();
        _tripGridComponent.refresh();
    }

    private static RadioButtonGroup<String> getSortControl() {
        RadioButtonGroup<String> sortControl = new RadioButtonGroup<>();
        sortControl.setItems("# of stops", "price");
        sortControl.setLabel("Sort by");

        return sortControl;
    }
}
