package com.newpathfly.flight.search.webapp.adapter;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import com.newpathfly.model.SegmentGroup;
import com.newpathfly.model.Trip;

public class TripAdapter extends Trip {

    private final List<SegmentGroup> _segmentGroups;

    private final long _timestamp;

    public TripAdapter(Trip trip) {
        setFlights(trip.getFlights());
        setPrices(trip.getPrices());

        _segmentGroups = trip.getSegmentGroups();

        _timestamp = System.currentTimeMillis();
    }

    public long getTimestamp() {
        return _timestamp;
    }


    @Override
    public @Valid @Size(min = 1) List<SegmentGroup> getSegmentGroups() {
        return _segmentGroups;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TripAdapter))
            return false;

        if (_timestamp != ((TripAdapter) o).getTimestamp())
            return false;

        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return (int) ((super.hashCode() + _timestamp) % Integer.MAX_VALUE);
    }

}
