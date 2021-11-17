package com.newpathfly.flight.search.webapp.adapter;

import com.newpathfly.model.Price;

public class PriceAdapter extends Price {
    
    private final long _timestamp;

    public PriceAdapter(Price price) {
        _timestamp = System.currentTimeMillis();

        setCurrencyCode(price.getCurrencyCode());
        setPrice(price.getPrice());
        setTax(price.getTax());
        setTotalPrice(price.getTotalPrice());
        setSeatsStatus(price.getSeatsStatus());
    }

    public long getTimestamp() {
        return _timestamp;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PriceAdapter))
            return false;

        if (_timestamp != ((PriceAdapter)o).getTimestamp())
            return false;

        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return (int)((super.hashCode() + _timestamp) % Integer.MAX_VALUE);
    }
}
