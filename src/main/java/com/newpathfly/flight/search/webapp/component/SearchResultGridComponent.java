package com.newpathfly.flight.search.webapp.component;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

import com.newpathfly.flight.search.webapp.model.SortTypeEnum;
import com.newpathfly.model.Flight;
import com.newpathfly.model.Price;
import com.newpathfly.model.Trip;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.TextRenderer;

public class SearchResultGridComponent extends VerticalLayout {

    private final PriorityBlockingQueue<Trip> _tripsQueueByPrice;
    private final PriorityBlockingQueue<Trip> _tripsQueueByStops;

    // UI
    private final RadioButtonGroup<SortTypeEnum> _sortControl;
    private final TripGridComponent _tripGridComponent;

    public SearchResultGridComponent() {

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
        switchSortType(_sortControl.getValue());

        // UI events
        _sortControl.addValueChangeListener(e -> switchSortType(e.getValue()));

        add( //
                sortControlLayout, //
                _tripGridComponent //
        );

        // misc settings
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
        _tripsQueueByPrice.addAll(trips);
        _tripsQueueByStops.addAll(trips);

        _tripGridComponent.refresh();
    }

    public void clear() {
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
            setDataProvider(_tripsQueueByPrice);
            break;

        case BY_STOPS:
            setDataProvider(_tripsQueueByStops);
            break;

        default:
            // do nothing
        }

        _tripGridComponent.refresh();
    }

    private void setDataProvider(Collection<Trip> trips) {
        // lazy loading for performance
        _tripGridComponent.setDataProvider(DataProvider.fromCallbacks( //
                q -> {
                    int offset = q.getOffset();
                    int limit = q.getLimit();
                    return trips.stream().skip(offset).limit(limit);
                }, //
                q -> {
                    return trips.size();
                } //
        ));
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
