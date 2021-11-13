package com.newpathfly.flight.search.webapp.component;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class LogNotification extends Notification {
    private LogNotification(NotificationVariant variant, String message) {
        super(message);
        addThemeVariants(variant);

        switch(variant){
            case LUMO_ERROR:
                setDuration(10000);
                setPosition(Position.MIDDLE);
                break;
            case LUMO_SUCCESS:
                setDuration(3000);
                setPosition(Position.BOTTOM_STRETCH);
                break;
            default:
                setDuration(1000);
                setPosition(Position.BOTTOM_STRETCH);
                break;
        }
    }

    public static void send(NotificationVariant variant, String message) {
        new LogNotification(variant, message).open();
    }
}
