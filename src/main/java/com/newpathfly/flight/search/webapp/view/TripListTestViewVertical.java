package com.newpathfly.flight.search.webapp.view;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newpathfly.flight.search.webapp.component.TripComponentVertical;
import com.newpathfly.flight.search.webapp.component.TripListComponent;
import com.newpathfly.model.PollResponse;
import com.newpathfly.model.Trip;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import lombok.SneakyThrows;

@Route(value = "/test1")
public class TripListTestViewVertical extends VerticalLayout {

    private final TripListComponent _tripListComponent;

    private final transient ResourceLoader _resourceLoader;
    private final transient ObjectMapper _objectMapper;

    public TripListTestViewVertical(@Autowired ResourceLoader resourceLoader) {
        _tripListComponent = new TripListComponent();
        _resourceLoader = resourceLoader;
        _objectMapper = new ObjectMapper();

        add(_tripListComponent);

        buildTripList().forEach(t -> {
            _tripListComponent.add(new TripComponentVertical(t));
        });

        setSpacing(false);
    }

    @SneakyThrows
    private List<Trip> buildTripList() {
        Resource jsonResource = _resourceLoader.getResource("classpath:sample/pollresponse_ow.json");

        PollResponse pollResponse = _objectMapper.readValue(jsonResource.getFile(), PollResponse.class);

        return pollResponse.getTrips();
    }
}
