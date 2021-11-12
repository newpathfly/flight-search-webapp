package com.newpathfly.flight.search.webapp.event;

public class ErrorEvent implements IEvent {

    private final String _message;

    public ErrorEvent(String message) {
        _message = message;
    }

    public String getMessage() {
        return _message;
    }
    
}
