package com.newpathfly.flight.search.webapp.component;

import java.time.LocalDate;

import com.newpathfly.model.SearchRequest;
import com.newpathfly.model.TripType;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;

public class SearchComponent extends CustomField<SearchRequest> {

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

        _depDatePicker = buildDatePicker("Departure Date", LocalDate.now().plusWeeks(1));
        _retDatePicker = buildDatePicker("Return Date", LocalDate.now().plusWeeks(2));

        // create event handlers
        _tripTypeRadioButtonGroup.addValueChangeListener(e -> {
            _retDatePicker.setEnabled(TripType.RT.equals(e.getValue()));
        });

        add(buildHorizontalLayout());
    }

    @Override
    protected SearchRequest generateModelValue() {
        return new SearchRequest();
    }

    @Override
    protected void setPresentationValue(SearchRequest searchRequest) {
    }

    private HorizontalLayout buildHorizontalLayout() {
        HorizontalLayout layout = new HorizontalLayout( //
                _tripTypeRadioButtonGroup, //
                _depAirportTextField, //
                _arrAirportTextField, //
                _depDatePicker, //
                _retDatePicker, //
                new Button("Search", VaadinIcon.SEARCH.create()));

        layout.setAlignItems(Alignment.END);
        layout.setJustifyContentMode(JustifyContentMode.CENTER);

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

    private DatePicker buildDatePicker(String label, LocalDate defaultDate) {
        DatePicker datePicker = new DatePicker(label);
        datePicker.setValue(defaultDate);
        return datePicker;
    }

}
