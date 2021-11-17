package com.newpathfly.flight.search.webapp.model;

public enum SortTypeEnum {

    BY_PRICE("price"),

    BY_STOPS("# of stops"),

    NONE("none");

    private final String _value;

    private SortTypeEnum(String value) {
        _value = value;
    }

    public String getValue() {
        return _value;
    }
}
