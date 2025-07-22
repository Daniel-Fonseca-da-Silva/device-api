package com.dafon.device_api.controller.dto;

import com.dafon.device_api.model.DeviceState;

public record UpdateDeviceDto(String name, String brand, DeviceState state) {

}
