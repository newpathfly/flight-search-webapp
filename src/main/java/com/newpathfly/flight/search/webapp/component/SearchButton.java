package com.newpathfly.flight.search.webapp.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;

public class SearchButton extends Button {
    
    public SearchButton() {
        super("Search");
        setIcon(VaadinIcon.SEARCH.create());
    }
}
