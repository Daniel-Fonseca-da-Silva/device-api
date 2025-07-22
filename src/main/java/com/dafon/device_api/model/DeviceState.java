package com.dafon.device_api.model;

public enum DeviceState {
    AVAILABLE("Available"),
    IN_USE("In Use"),
    INACTIVE("Inactive");

    private final String description;

    DeviceState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
