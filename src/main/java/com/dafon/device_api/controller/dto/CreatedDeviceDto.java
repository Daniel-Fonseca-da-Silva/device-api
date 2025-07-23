package com.dafon.device_api.controller.dto;

import com.dafon.device_api.entity.DeviceState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatedDeviceDto(@NotBlank String name, @NotBlank String brand, @NotNull DeviceState state) {

}
