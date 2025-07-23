package com.dafon.device_api.service;

import com.dafon.device_api.controller.dto.CreatedDeviceDto;
import com.dafon.device_api.controller.dto.UpdateDeviceDto;
import com.dafon.device_api.exception.DeviceInUseException;
import com.dafon.device_api.exception.InvalidFieldException;
import com.dafon.device_api.entity.Device;
import com.dafon.device_api.entity.DeviceState;
import com.dafon.device_api.exception.NotFoundException;
import com.dafon.device_api.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Device createDevice(CreatedDeviceDto dto) {
        Device device = new Device();
        device.setName(dto.name());
        device.setBrand(dto.brand());
        device.setState(dto.state());
        device.setCreationTime(java.time.LocalDateTime.now());
        return deviceRepository.save(device);
    }

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public List<Device> findByBrand(String brand) {
        return deviceRepository.findByBrand(brand);
    }

    public List<Device> findByState(DeviceState state) {
        return deviceRepository.findByState(state);
    }

    public Device findById(Long id) {
        return deviceRepository.findById(id).orElse(null);
    }

    // Updates an existing device with the provided data. Only non-null fields are updated. Throws exceptions if invalid or not allowed.
    public Device updateDevice(Long id, UpdateDeviceDto dto) {
        // Check if at least one field is provided
        if (dto.name() == null && dto.brand() == null && dto.state() == null) {
            throw new InvalidFieldException();
        }
        // Check if the device exists
        Device device = findById(id);
        if (device == null) {
            throw new NotFoundException();
        }
        // If the device is in use, only the state can be updated
        if (device.getState() == DeviceState.IN_USE) {
            if (!device.getName().equals(dto.name()) || !device.getBrand().equals(dto.brand())) {
                throw new DeviceInUseException();
            }

        }

        // Update the device with the provided data, only if the field is not null and only to name, brand or state
        if (dto.name() != null) {
            device.setName(dto.name());
        }
        if (dto.brand() != null) {
            device.setBrand(dto.brand());
        }
        if (dto.state() != null) {
            device.setState(dto.state());
        }

        return deviceRepository.save(device);
    }

    // Deletes a device. Throws exceptions if the device is in use or not found.
    public void deleteDevice(Long id) {
        // Check if the device exists
        Device device = findById(id);
        // If the device does not exist, throw an exception
        if (device == null) {
            throw new NotFoundException();
        }
        // If the device is in use, throw an exception
        if (device.getState() == DeviceState.IN_USE) {
            throw new DeviceInUseException();
        }
        deviceRepository.deleteById(id);
    }

}

