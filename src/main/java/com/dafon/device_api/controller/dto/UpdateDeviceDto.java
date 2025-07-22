package com.dafon.device_api.controller.dto;

import com.dafon.device_api.model.DeviceState;
import jakarta.validation.constraints.NotNull;

public record UpdateDeviceDto(String name, String brand, @NotNull DeviceState state) {

}
