package com.newpathfly.flight.search.webapp.component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.newpathfly.flight.search.webapp.adapter.TripAdapter;
import com.newpathfly.flight.search.webapp.model.SortTypeEnum;
import com.newpathfly.model.Flight;
import com.newpathfly.model.Price;
import com.newpathfly.model.Trip;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.TextRenderer;

public class SearchResultComponent extends VerticalLayout {

    private final transient List<TripAdapter> _trips;

    // UI
    private final RadioButtonGroup<SortTypeEnum> _sortControl;
    private final Div _noResultDiv;
    private final TripGrid _tripGridComponent;

    public SearchResultComponent() {

        _trips = new ArrayList<>();

        // constructors
        _sortControl = buildSortControl();
        HorizontalLayout sortControlLayout = new HorizontalLayout(_sortControl);
        sortControlLayout.setJustifyContentMode(JustifyContentMode.END);
        sortControlLayout.setWidthFull();

        _noResultDiv = new Div();
        _noResultDiv.add("No result at the moment");
        _noResultDiv.getStyle().set("font-weight", "lighter");

        _tripGridComponent = new TripGrid();
        switchSortType(_sortControl.getValue());

        // UI events
        _sortControl.addValueChangeListener(e -> switchSortType(e.getValue()));

        add( //
                sortControlLayout, //
                _noResultDiv, //
                _tripGridComponent //
        );

        // misc settings
        setSpacing(false);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setWidth("956px");
        setHeightFull();

        getStyle().set("border-width", "1px");
        getStyle().set("border-color", "#AAAAAA");
        getStyle().set("border-top-style", "solid");
    }

    public TripGrid getTripGridComponent() {
        return _tripGridComponent;
    }

    public void add(List<TripAdapter> trips) {
        _trips.addAll(trips);

        refresh();
    }

    public void clear() {
        _trips.clear();

        refresh();
    }

    public int size() {
        return _trips.size();
    }

    private void switchSortType(SortTypeEnum sortType) {
        switch (sortType) {
        case BY_PRICE:
            setDataProvider(comparatorByPrice);
            break;

        case BY_STOPS:
            setDataProvider(comparatorByStops);
            break;

        default:
            // do nothing
        }

        refresh();
    }

    private void refresh() {

        _noResultDiv.setVisible(_trips.isEmpty());

        _tripGridComponent.getDataProvider().refreshAll();
    }

    private void setDataProvider(Comparator<TripAdapter> comparator) {
        // lazy loading for performance
        _tripGridComponent.setDataProvider(DataProvider.fromCallbacks( //
                q -> {
                    int offset = q.getOffset();
                    int limit = q.getLimit();
                    return _trips.stream().sorted(comparator).skip(offset).limit(limit);
                }, //
                q -> {
                    return _trips.size();
                } //
        ));
    }

    private static RadioButtonGroup<SortTypeEnum> buildSortControl() {
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
        Price price = trip.getPrices().getALL();

        return null == price ? -1 : price.getTotalPrice();
    }

    private static Comparator<TripAdapter> comparatorByPrice = (t1, t2) -> {
        double p1 = getTotalPrice(t1);
        double p2 = getTotalPrice(t2);

        return Double.compare(p1, p2);
    };

    private static Comparator<TripAdapter> comparatorByStops = (t1, t2) -> {
        int s1 = getStopCount(t1);
        int s2 = getStopCount(t2);

        return Integer.compare(s1, s2);
    };
}
