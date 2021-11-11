package com.newpathfly.flight.search.webapp.event;

import org.springframework.context.ApplicationEvent;

// @todo add subscription to this event somewhere
public class CancelPollingEvent extends ApplicationEvent {

    public CancelPollingEvent(Object source) {
        super(source);
    }    
}
