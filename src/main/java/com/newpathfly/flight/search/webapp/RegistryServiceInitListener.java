package com.newpathfly.flight.search.webapp;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.UIInitEvent;
import com.vaadin.flow.server.UIInitListener;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.VaadinSession;

public class RegistryServiceInitListener implements VaadinServiceInitListener, UIInitListener {
    @Override
    public void serviceInit(ServiceInitEvent serviceInitEvent) {
        serviceInitEvent.getSource().addUIInitListener(this);
    }

    @Override
    public void uiInit(UIInitEvent uiInitEvent) {
        final VaadinSession session = uiInitEvent.getUI().getSession();
        session.setAttribute(EventRegistry.class, null);
        session.setAttribute(EventRegistry.class, new EventRegistry());
    }
}