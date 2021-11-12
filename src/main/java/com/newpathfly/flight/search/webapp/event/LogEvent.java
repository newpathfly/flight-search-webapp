package com.newpathfly.flight.search.webapp.event;

import com.newpathfly.flight.search.webapp.event.common.IEvent;
import com.vaadin.flow.component.notification.NotificationVariant;

public class LogEvent implements IEvent {


    private final NotificationVariant _variant;
    private final String _message;

    public LogEvent(NotificationVariant variant, String message) {
        _variant = variant;
        _message = message;
    }

    public NotificationVariant getVariant() {
        return _variant;
    }

    public String getMessage() {
        return _message;
    }
    
}
