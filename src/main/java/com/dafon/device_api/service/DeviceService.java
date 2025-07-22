package com.dafon.device_api.service;

import com.dafon.device_api.controller.dto.CreatedDeviceDto;
import com.dafon.device_api.model.Device;
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
    
}
