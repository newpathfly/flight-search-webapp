package com.newpathfly.flight.search.webapp.view;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newpathfly.flight.search.webapp.component.SearchResultComponent;
import com.newpathfly.flight.search.webapp.component.TripComponent;
import com.newpathfly.model.PollResponse;
import com.newpathfly.model.Trip;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import lombok.SneakyThrows;

@Route(value = "/test2")
public class TripListTestView extends VerticalLayout {

    private final SearchResultComponent _searchResultComponent;

    private final transient ResourceLoader _resourceLoader;
    private final transient ObjectMapper _objectMapper;

    public TripListTestView(@Autowired ResourceLoader resourceLoader) {
        _searchResultComponent = new SearchResultComponent();
        _resourceLoader = resourceLoader;
        _objectMapper = new ObjectMapper();

        add(_searchResultComponent);

        buildTripList().forEach(t -> {
            _searchResultComponent.getTripListComponent().add(new TripComponent(t));
        });

        setSpacing(false);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
    }

    @SneakyThrows
    private List<Trip> buildTripList() {
        Resource jsonResource = _resourceLoader.getResource("classpath:sample/pollresponse_ow.json");

        PollResponse pollResponse = _objectMapper.readValue(jsonResource.getFile(), PollResponse.class);

        return pollResponse.getTrips();
    }
}
