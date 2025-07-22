package com.dafon.device_api.controller;

import com.dafon.device_api.controller.dto.CreatedDeviceDto;
import com.dafon.device_api.controller.dto.UpdateDeviceDto;
import com.dafon.device_api.exception.NotFoundException;
import com.dafon.device_api.model.Device;
import com.dafon.device_api.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<Device>> getAllDevices() {
        var devices = deviceService.getAllDevices();
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Device>> getDevicesByBrand(@PathVariable String brand) {
        List<Device> devices = deviceService.findByBrand(brand);
        if (devices.isEmpty()) {
            throw new NotFoundException();
        }
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable Long id) {
        Device device = deviceService.findById(id);
        if (device == null) {
            throw new NotFoundException();
        }
        return ResponseEntity.ok(device);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable Long id, @RequestBody @Valid UpdateDeviceDto dto) {
        Device updated = deviceService.updateDevice(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        if (deviceService.findById(id) == null) {
            throw new NotFoundException();
        }
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }
}