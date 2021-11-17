package com.newpathfly.flight.search.webapp.view;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newpathfly.flight.search.webapp.adapter.TripAdapter;
import com.newpathfly.flight.search.webapp.component.SearchResultComponent;
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
public class TestView extends VerticalLayout {

    private final Button _addButton;
    private final Button _clearButton;
    private final SearchResultComponent _searchResultGridComponent;

    private final transient ResourceLoader _resourceLoader;
    private final transient ObjectMapper _objectMapper;

    public TestView(@Autowired ResourceLoader resourceLoader) {

        _resourceLoader = resourceLoader;
        _objectMapper = new ObjectMapper();

        _addButton = new Button("Add");
        _clearButton = new Button("Clear");
        _searchResultGridComponent = new SearchResultComponent();

        _addButton.addClickListener(e -> {
            _searchResultGridComponent.add(buildTripList().stream().map(TripAdapter::new).collect(Collectors.toList()));
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
