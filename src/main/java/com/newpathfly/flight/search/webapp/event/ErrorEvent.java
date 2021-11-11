package com.newpathfly.flight.search.webapp.event;

import org.springframework.context.ApplicationEvent;

public class ErrorEvent extends ApplicationEvent {

    private final String _message;

    public ErrorEvent(String message) {
        super(message);

        _message = message;
    }

    public String getMessage() {
        return _message;
    }
    
}
