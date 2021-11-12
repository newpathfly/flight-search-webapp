package com.newpathfly.flight.search.webapp.component;

import com.newpathfly.model.Segment;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class SegmentComponent extends FormLayout {

    private final Segment _segment;
    private final Binder<Segment> _binder;

    private final TextField _depDateTextField;
    private final TextField _depTimeTextField;

    public SegmentComponent(Segment segment) {
        // initialize
        _segment = segment;
        _binder = new Binder<>(Segment.class);

        // construct
        _depDateTextField = new TextField();
        _depTimeTextField = new TextField();

        // @todo more fields here

        add( //
                _depDateTextField, //
                _depTimeTextField //
        );

        // bind
        _binder.forField(_depDateTextField).bind(Segment::getDepDate, null);
        _binder.forField(_depTimeTextField).bind(Segment::getDepTime, null);
        _binder.readBean(_segment);
    }

    public Segment getSegment() {
        return _segment;
    }
}
