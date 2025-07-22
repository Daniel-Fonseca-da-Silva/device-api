package com.dafon.device_api.service;

import com.dafon.device_api.controller.dto.CreatedDeviceDto;
import com.dafon.device_api.controller.dto.UpdateDeviceDto;
import com.dafon.device_api.exception.DeviceInUseException;
import com.dafon.device_api.model.Device;
import com.dafon.device_api.model.DeviceState;
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

    public Device updateDevice(Long id, UpdateDeviceDto dto) {
        Device device = findById(id);

        if (device == null) {
            throw new NotFoundException();
        }
        
        if (device.getState() == DeviceState.IN_USE) {
            if (!device.getName().equals(dto.name()) || !device.getBrand().equals(dto.brand())) {
                throw new DeviceInUseException();
            }

        }

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

    public void deleteDevice(Long id) {
        Device device = findById(id);
        if (device == null) {
            throw new NotFoundException();
        }
        if (device.getState() == DeviceState.IN_USE) {
            throw new DeviceInUseException();
        }
        deviceRepository.deleteById(id);
    }

}

