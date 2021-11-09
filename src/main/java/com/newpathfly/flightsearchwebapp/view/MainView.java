package com.newpathfly.flightsearchwebapp.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "")
public class MainView extends VerticalLayout {

    private final H1 _headline;

    public MainView() {

        _headline = new H1("Welcome to the Flight Search Web App");

        add( //
                _headline //
        );
    }
}
