package com.newpathfly.flight.search.webapp.view;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newpathfly.flight.search.webapp.component.SearchResultGridComponent;
import com.newpathfly.model.PollResponse;
import com.newpathfly.model.Trip;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import lombok.SneakyThrows;

@Route(value = "/test1")
public class TripGridTestView extends VerticalLayout {

    private final Button _addButton;
    private final Button _clearButton;
    private final SearchResultGridComponent _searchResultGridComponent;

    private final transient ResourceLoader _resourceLoader;
    private final transient ObjectMapper _objectMapper;

    public TripGridTestView(@Autowired ResourceLoader resourceLoader) {

        _resourceLoader = resourceLoader;
        _objectMapper = new ObjectMapper();

        _addButton = new Button("Add");
        _clearButton = new Button("Clear");
        _searchResultGridComponent = new SearchResultGridComponent();

        _addButton.addClickListener(e -> {
            _searchResultGridComponent.add(buildTripList());
        });

        _clearButton.addClickListener(e -> {
            _searchResultGridComponent.clear();
        });

        add(new HorizontalLayout(_addButton, _clearButton));
        add(_searchResultGridComponent);

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
