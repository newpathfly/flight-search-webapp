package com.newpathfly.flight.search.webapp.component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

import com.newpathfly.flight.search.webapp.model.SortTypeEnum;
import com.newpathfly.model.Flight;
import com.newpathfly.model.Price;
import com.newpathfly.model.Trip;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.data.renderer.TextRenderer;

public class SearchResultGridComponent extends VerticalLayout {

    private final List<Trip> _trips;
    private final PriorityBlockingQueue<Trip> _tripsQueueByPrice;
    private final PriorityBlockingQueue<Trip> _tripsQueueByStops;

    // UI
    private final RadioButtonGroup<SortTypeEnum> _sortControl;
    private final TripGridComponent _tripGridComponent;

    public SearchResultGridComponent() {

        _trips = new ArrayList<>();
        _tripsQueueByPrice = new PriorityBlockingQueue<>(10,
                (a, b) -> Double.compare(getTotalPrice(a), getTotalPrice(b)));
        _tripsQueueByStops = new PriorityBlockingQueue<>(10,
                (a, b) -> Integer.compare(getStopCount(a), getStopCount(b)));

        // constructors
        _sortControl = getSortControl();
        HorizontalLayout sortControlLayout = new HorizontalLayout(_sortControl);
        sortControlLayout.setJustifyContentMode(JustifyContentMode.END);
        sortControlLayout.setWidthFull();

        _tripGridComponent = new TripGridComponent();
        switchSortType(SortTypeEnum.NONE);
        // switchSortType(_sortControl.getValue());

        // UI events
        // _sortControl.addValueChangeListener(e -> switchSortType(e.getValue()));

        // misc settings

        add( //
                sortControlLayout, //
                _tripGridComponent //
        );

        setSpacing(false);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setWidth("956px");
        setHeight("100vh");

        getStyle().set("border-width", "1px");
        getStyle().set("border-color", "#AAAAAA");
        getStyle().set("border-top-style", "dotted");
    }

    public TripGridComponent getTripGridComponent() {
        return _tripGridComponent;
    }

    public void add(List<Trip> trips) {
        _trips.addAll(trips);
        _tripsQueueByPrice.addAll(trips);
        _tripsQueueByStops.addAll(trips);

        _tripGridComponent.refresh();
    }

    public void clear() {
        _trips.clear();
        _tripsQueueByPrice.clear();
        _tripsQueueByStops.clear();

        _tripGridComponent.refresh();
    }

    public int size() {
        switch (_sortControl.getValue()) {
        case BY_PRICE:
            return _tripsQueueByPrice.size();
        case BY_STOPS:
            return _tripsQueueByStops.size();
        default:
            return 0;
        }
    }

    private void switchSortType(SortTypeEnum sortType) {
        switch (sortType) {
        case BY_PRICE:
            _tripGridComponent.setItems(_tripsQueueByPrice);
            break;

        case BY_STOPS:
            _tripGridComponent.setItems(_tripsQueueByStops);
            break;

        default:
            _tripGridComponent.setItems(_trips);
        }

        _tripGridComponent.refresh();
    }

    private static RadioButtonGroup<SortTypeEnum> getSortControl() {
        RadioButtonGroup<SortTypeEnum> sortControl = new RadioButtonGroup<>();
        sortControl.setItems(SortTypeEnum.BY_STOPS, SortTypeEnum.BY_PRICE);
        sortControl.setLabel("Sort by");
        sortControl.setValue(SortTypeEnum.BY_STOPS);
        sortControl.setRenderer(new TextRenderer<>(SortTypeEnum::getValue));

        return sortControl;
    }

    private static int getStopCount(Trip trip) {
        int stopCount = 0;

        for (Flight f : trip.getFlights()) {
            stopCount += f.getSegments().size() - 2;
        }

        return stopCount;
    }

    private static double getTotalPrice(Trip trip) {
        Price price = trip.getPrices().getADT();

        return null == price ? -1 : price.getTotalPrice();
    }
}
