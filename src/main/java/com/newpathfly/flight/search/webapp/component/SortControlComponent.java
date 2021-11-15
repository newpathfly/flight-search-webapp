package com.newpathfly.flight.search.webapp.component;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;

public class SortControlComponent extends HorizontalLayout {

    private final RadioButtonGroup<String> _sortControl;

    public SortControlComponent() {
        _sortControl = getSortControl();

        setWidth("956px");
        setJustifyContentMode(JustifyContentMode.END);

        add(_sortControl);

        getStyle().set("border-width", "1px");
        getStyle().set("border-color", "#AAAAAA");
        getStyle().set("border-top-style", "dotted");
    }

    private static RadioButtonGroup<String> getSortControl() {
        RadioButtonGroup<String> sortControl = new RadioButtonGroup<>();

        sortControl.setItems("# of stops", "price");
        sortControl.setLabel("Sort by");

        return sortControl;
    }
}
