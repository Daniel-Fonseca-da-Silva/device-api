package com.dafon.device_api.service;

import com.dafon.device_api.controller.dto.CreatedDeviceDto;
import com.dafon.device_api.exception.DeviceInUseException;
import com.dafon.device_api.model.Device;
import com.dafon.device_api.model.DeviceState;
import com.dafon.device_api.exception.NoFoundException;
import com.dafon.device_api.exception.CustomException;
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

    public Device findById(Long id) {
        return deviceRepository.findById(id).orElse(null);
    }

    public void deleteDevice(Long id) {
        Device device = findById(id);
        if (device == null) {
            throw new NoFoundException();
        }
        if (device.getState() == DeviceState.IN_USE) {
            throw new DeviceInUseException();
        }
        deviceRepository.deleteById(id);
    }

}
