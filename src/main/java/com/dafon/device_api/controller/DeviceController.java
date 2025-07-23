package com.dafon.device_api.controller;

import com.dafon.device_api.controller.dto.CreatedDeviceDto;
import com.dafon.device_api.controller.dto.UpdateDeviceDto;
import com.dafon.device_api.exception.NotFoundException;
import com.dafon.device_api.entity.Device;
import com.dafon.device_api.entity.DeviceState;
import com.dafon.device_api.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    @Operation(summary = "Create a new device")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Device created"),
        @ApiResponse(responseCode = "400", description = "Invalid request body"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Device> createDevice(@RequestBody @Valid CreatedDeviceDto dto) {
        var device = deviceService.createDevice(dto);
        return ResponseEntity.ok(device);
    }

    @GetMapping
    @Operation(summary = "Get all devices")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Devices found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Device>> getAllDevices() {
        var devices = deviceService.getAllDevices();
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/brand/{brand}")
    @Operation(summary = "Get devices by brand")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Devices found"),
        @ApiResponse(responseCode = "404", description = "Devices not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Device>> getDevicesByBrand(@PathVariable String brand) {
        List<Device> devices = deviceService.findByBrand(brand);
        if (devices.isEmpty()) {
            throw new NotFoundException();
        }
        return ResponseEntity.ok(devices);
    }

    @Operation(summary = "Get devices by state")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Devices found"),
        @ApiResponse(responseCode = "404", description = "Devices not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/state/{state}")
    public ResponseEntity<List<Device>> getDevicesByState(@PathVariable String state) {
        DeviceState deviceState;

        try {
            deviceState = DeviceState.valueOf(state.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new NotFoundException();
        }
        
        List<Device> devices = deviceService.findByState(deviceState);
        if(devices.isEmpty()) {
            throw new NotFoundException();
        }
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get device by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Device found"),
        @ApiResponse(responseCode = "404", description = "Device not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Device> getDeviceById(@PathVariable Long id) {
        Device device = deviceService.findById(id);
        if (device == null) {
            throw new NotFoundException();
        }
        return ResponseEntity.ok(device);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update a device")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Device updated"),
        @ApiResponse(responseCode = "404", description = "Device not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Device> updateDevice(@PathVariable Long id, @RequestBody @Valid UpdateDeviceDto dto) {
        Device updated = deviceService.updateDevice(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a device")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Device deleted"),
        @ApiResponse(responseCode = "404", description = "Device not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        if (deviceService.findById(id) == null) {
            throw new NotFoundException();
        }
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }
}