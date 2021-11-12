package com.newpathfly.flight.search.webapp.component;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class InfoNotification extends Notification {
    private InfoNotification(String message) {
        super(message);
        addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        setDuration(3000);
        setPosition(Position.MIDDLE);
    }

    public static void send(String message) {
        new InfoNotification(message).open();
    }
}