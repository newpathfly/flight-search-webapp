package com.newpathfly.flight.search.webapp.component;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;

public class SearchResultComponent extends VerticalLayout {

    // UI
    private final RadioButtonGroup<String> _sortControl;
    private final TripListComponent _tripListComponent;

    public SearchResultComponent() {

        // constructors
        _sortControl = getSortControl();
        HorizontalLayout sortControlLayout = new HorizontalLayout(_sortControl);
        sortControlLayout.setJustifyContentMode(JustifyContentMode.END);
        sortControlLayout.setWidthFull();

        _tripListComponent = new TripListComponent();

        // misc settings

        add( //
                sortControlLayout, //
                _tripListComponent //
        );

        setSpacing(false);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setWidth("956px");

        getStyle().set("border-width", "1px");
        getStyle().set("border-color", "#AAAAAA");
        getStyle().set("border-top-style", "dotted");
    }

    public TripListComponent getTripListComponent() {
        return _tripListComponent;
    }

    public void clear() {
        _tripListComponent.clear();
    }

    private static RadioButtonGroup<String> getSortControl() {
        RadioButtonGroup<String> sortControl = new RadioButtonGroup<>();
        sortControl.setItems("# of stops", "price");
        sortControl.setLabel("Sort by");

        return sortControl;
    }
}
