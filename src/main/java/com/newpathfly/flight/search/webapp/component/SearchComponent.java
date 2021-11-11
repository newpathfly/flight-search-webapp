package com.newpathfly.flight.search.webapp.component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

import com.newpathfly.model.FareClass;
import com.newpathfly.model.Query;
import com.newpathfly.model.SearchRequest;
import com.newpathfly.model.TripType;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;

public class SearchComponent extends CustomField<SearchRequest> {

    public static final DateTimeFormatter DATE_FORMAT_YYYYMMDD = DateTimeFormatter.ofPattern("uuuuMMdd")
            .withResolverStyle(ResolverStyle.STRICT);

    private final RadioButtonGroup<TripType> _tripTypeRadioButtonGroup;

    private final TextField _depAirportTextField;
    private final TextField _arrAirportTextField;

    private final DatePicker _depDatePicker;
    private final DatePicker _retDatePicker;

    public SearchComponent() {
        // create components
        _tripTypeRadioButtonGroup = buildTripTypeRadioButtonGroup();

        _depAirportTextField = buildAirportTextField("Departure Airport", VaadinIcon.FLIGHT_TAKEOFF);
        _arrAirportTextField = buildAirportTextField("Arrival Airport", VaadinIcon.FLIGHT_LANDING);

        _depDatePicker = buildDatePicker("Departure Date", LocalDate.now().plusWeeks(1), LocalDate.now().plusDays(1));
        _retDatePicker = buildDatePicker("Return Date", LocalDate.now().plusWeeks(2), _depDatePicker.getValue());
        _depDatePicker.addValueChangeListener(e -> {
            // whenever departure date changes, update minimum return date
            if (_retDatePicker.getValue().isBefore(e.getValue().plusDays(1))) {
                _retDatePicker.clear();
            }
            _retDatePicker.setMin(e.getValue().plusDays(1));
        });

        // create event handlers
        _tripTypeRadioButtonGroup.addValueChangeListener(e -> {
            _retDatePicker.setEnabled(TripType.RT.equals(e.getValue()));
        });

        add(buildHorizontalLayout());
    }

    @Override
    protected SearchRequest generateModelValue() {

        TripType tripType = _tripTypeRadioButtonGroup.getValue();

        List<Query> queries = new ArrayList<>(TripType.OW.equals(tripType) ? 1 : 2);

        queries.add( //
                new Query() //
                        .depAirport(_depAirportTextField.getValue()) //
                        .arrAirport(_arrAirportTextField.getValue()) //
                        .date(_depDatePicker.getValue().format(DATE_FORMAT_YYYYMMDD)) //
        );

        if (TripType.RT.equals(tripType)) {
            queries.add( //
                    new Query() //
                            .depAirport(_arrAirportTextField.getValue()) //
                            .arrAirport(_depAirportTextField.getValue()) //
                            .date(_retDatePicker.getValue().format(DATE_FORMAT_YYYYMMDD)) //
            );
        }

        return new SearchRequest() //
                .queries(queries) //
                .type(tripType) //
                .adultAmount(1) //
                .childAmount(0) //
                .infantAmount(0) //
                .fareClass(FareClass.ECONOMY);
    }

    @Override
    protected void setPresentationValue(SearchRequest searchRequest) {
        _tripTypeRadioButtonGroup.setValue(searchRequest.getType());

        _depAirportTextField.setValue(searchRequest.getQueries().get(0).getDepAirport());
        _arrAirportTextField.setValue(searchRequest.getQueries().get(0).getArrAirport());

        _depDatePicker.setValue(LocalDate.parse(searchRequest.getQueries().get(0).getDate(), DATE_FORMAT_YYYYMMDD));
        if (TripType.RT.equals(searchRequest.getType())) {
            _retDatePicker.setValue(LocalDate.parse(searchRequest.getQueries().get(1).getDate(), DATE_FORMAT_YYYYMMDD));
        }
    }

    private HorizontalLayout buildHorizontalLayout() {
        HorizontalLayout layout = new HorizontalLayout( //
                _tripTypeRadioButtonGroup, //
                _depAirportTextField, //
                _arrAirportTextField, //
                _depDatePicker, //
                _retDatePicker//
        );

        layout.setAlignItems(Alignment.END);
        layout.setJustifyContentMode(JustifyContentMode.CENTER);
        layout.setWidthFull();

        return layout;
    }

    private static TextField buildAirportTextField(String label, VaadinIcon icon) {
        TextField textField = new TextField();
        textField.setLabel(label);
        textField.setPrefixComponent(icon.create());
        return textField;
    }

    private static RadioButtonGroup<TripType> buildTripTypeRadioButtonGroup() {
        RadioButtonGroup<TripType> radioButtonGroup = new RadioButtonGroup<>();
        radioButtonGroup.setItems(TripType.OW, TripType.RT);
        radioButtonGroup.setValue(TripType.RT);
        return radioButtonGroup;
    }

    private DatePicker buildDatePicker(String label, LocalDate defaultDate, LocalDate minDate) {
        DatePicker datePicker = new DatePicker(label);
        datePicker.setValue(defaultDate);
        datePicker.setMin(minDate);
        datePicker.setMax(LocalDate.now().plusDays(180));
        datePicker.setClearButtonVisible(true);
        datePicker.setWidth("152px");
        return datePicker;
    }

}
