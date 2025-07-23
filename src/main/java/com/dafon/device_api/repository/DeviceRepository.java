package com.dafon.device_api.repository;

import com.dafon.device_api.entity.Device;
import com.dafon.device_api.entity.DeviceState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByBrand(String brand);
    List<Device> findByState(DeviceState state);
}
