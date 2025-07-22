package com.dafon.device_api.controller;

import com.dafon.device_api.controller.dto.CreatedDeviceDto;
import com.dafon.device_api.model.Device;
import com.dafon.device_api.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody @Valid CreatedDeviceDto dto) {
        var device = deviceService.createDevice(dto);
        return ResponseEntity.ok(device);
    }
}