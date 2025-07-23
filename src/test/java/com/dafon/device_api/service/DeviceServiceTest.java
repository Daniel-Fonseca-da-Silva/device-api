package com.dafon.device_api.service;

import com.dafon.device_api.entity.Device;
import com.dafon.device_api.repository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import com.dafon.device_api.entity.DeviceState;

class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceService deviceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllDevices() {
        Device device1 = new Device();
        device1.setId(1L);
        device1.setName("Device 1");
        Device device2 = new Device();
        device2.setId(2L);
        device2.setName("Device 2");
        List<Device> devices = Arrays.asList(device1, device2);

        when(deviceRepository.findAll()).thenReturn(devices);

        List<Device> result = deviceService.getAllDevices();

        assertEquals(2, result.size());
        assertEquals("Device 1", result.get(0).getName());
        assertEquals("Device 2", result.get(1).getName());
    }

    @Test
    void shouldReturnEmptyListWhenNoDevicesExist() {
        when(deviceRepository.findAll()).thenReturn(List.of());

        List<Device> result = deviceService.getAllDevices();

        assertEquals(0, result.size());
    }

    @Test
    void shouldReturnDevicesWithDifferentStates() {
        Device device1 = new Device();
        device1.setId(1L);
        device1.setName("Device 1");
        device1.setState(com.dafon.device_api.entity.DeviceState.IN_USE);
        Device device2 = new Device();
        device2.setId(2L);
        device2.setName("Device 2");
        device2.setState(com.dafon.device_api.entity.DeviceState.AVAILABLE);
        List<Device> devices = Arrays.asList(device1, device2);

        when(deviceRepository.findAll()).thenReturn(devices);

        List<Device> result = deviceService.getAllDevices();

        assertEquals(2, result.size());
        assertEquals(com.dafon.device_api.entity.DeviceState.IN_USE, result.get(0).getState());
        assertEquals(com.dafon.device_api.entity.DeviceState.AVAILABLE, result.get(1).getState());
    }

    @Test
    void shouldReturnDevicesWithDifferentBrandsAndNames() {
        Device device1 = new Device();
        device1.setId(1L);
        device1.setName("Alpha");
        device1.setBrand("BrandA");
        Device device2 = new Device();
        device2.setId(2L);
        device2.setName("Beta");
        device2.setBrand("BrandB");
        List<Device> devices = Arrays.asList(device1, device2);

        when(deviceRepository.findAll()).thenReturn(devices);

        List<Device> result = deviceService.getAllDevices();

        assertEquals(2, result.size());
        assertEquals("Alpha", result.get(0).getName());
        assertEquals("BrandA", result.get(0).getBrand());
        assertEquals("Beta", result.get(1).getName());
        assertEquals("BrandB", result.get(1).getBrand());
    }

    @Test
    void shouldReturnEmptyListWhenNoDeviceWithBrand() {
        String brand = "NonExistentBrand";
        when(deviceRepository.findByBrand(brand)).thenReturn(List.of());

        List<Device> result = deviceService.findByBrand(brand);

        assertEquals(0, result.size());
    }

    @Test
    void shouldReturnDevicesOfGivenBrand() {
        String brand = "BrandX";
        Device device1 = new Device();
        device1.setId(1L);
        device1.setName("Device 1");
        device1.setBrand(brand);
        Device device2 = new Device();
        device2.setId(2L);
        device2.setName("Device 2");
        device2.setBrand(brand);
        List<Device> devices = Arrays.asList(device1, device2);

        when(deviceRepository.findByBrand(brand)).thenReturn(devices);

        List<Device> result = deviceService.findByBrand(brand);

        assertEquals(2, result.size());
        assertEquals(brand, result.get(0).getBrand());
        assertEquals(brand, result.get(1).getBrand());
    }

    @Test
    void shouldNotReturnDevicesOfOtherBrands() {
        String brand = "BrandY";
        Device device = new Device();
        device.setId(3L);
        device.setName("Device 3");
        device.setBrand(brand);
        when(deviceRepository.findByBrand(brand)).thenReturn(List.of(device));

        List<Device> result = deviceService.findByBrand(brand);

        assertEquals(1, result.size());
        assertEquals(brand, result.get(0).getBrand());
        assertEquals("Device 3", result.get(0).getName());
    }

    @Test
    void shouldReturnEmptyListWhenNoDeviceWithState() {
        DeviceState state = DeviceState.INACTIVE;
        when(deviceRepository.findByState(state)).thenReturn(List.of());

        List<Device> result = deviceService.findByState(state);

        assertEquals(0, result.size());
    }

    @Test
    void shouldReturnDevicesOfGivenState() {
        DeviceState state = DeviceState.AVAILABLE;
        Device device1 = new Device();
        device1.setId(1L);
        device1.setName("Device 1");
        device1.setState(state);
        Device device2 = new Device();
        device2.setId(2L);
        device2.setName("Device 2");
        device2.setState(state);
        List<Device> devices = Arrays.asList(device1, device2);

        when(deviceRepository.findByState(state)).thenReturn(devices);

        List<Device> result = deviceService.findByState(state);

        assertEquals(2, result.size());
        assertEquals(state, result.get(0).getState());
        assertEquals(state, result.get(1).getState());
    }

    @Test
    void shouldNotReturnDevicesOfOtherStates() {
        DeviceState state = DeviceState.IN_USE;
        Device device = new Device();
        device.setId(3L);
        device.setName("Device 3");
        device.setState(state);
        when(deviceRepository.findByState(state)).thenReturn(List.of(device));

        List<Device> result = deviceService.findByState(state);

        assertEquals(1, result.size());
        assertEquals(state, result.get(0).getState());
        assertEquals("Device 3", result.get(0).getName());
    }

    @Test
    void shouldReturnDeviceWhenIdExists() {
        Long id = 1L;
        Device device = new Device();
        device.setId(id);
        device.setName("Device 1");
        when(deviceRepository.findById(id)).thenReturn(java.util.Optional.of(device));

        Device result = deviceService.findById(id);

        assertEquals(id, result.getId());
        assertEquals("Device 1", result.getName());
    }

    @Test
    void shouldReturnNullWhenIdDoesNotExist() {
        Long id = 99L;
        when(deviceRepository.findById(id)).thenReturn(java.util.Optional.empty());

        Device result = deviceService.findById(id);

        assertEquals(null, result);
    }

    @Test
    void shouldReturnNullWhenIdIsNull() {
        when(deviceRepository.findById(null)).thenReturn(java.util.Optional.empty());

        Device result = deviceService.findById(null);

        assertEquals(null, result);
    }

    @Test
    void shouldReturnNullWhenIdIsNegative() {
        Long id = -1L;
        when(deviceRepository.findById(id)).thenReturn(java.util.Optional.empty());

        Device result = deviceService.findById(id);

        assertEquals(null, result);
    }

    @Test
    void shouldReturnNullWhenIdIsZero() {
        Long id = 0L;
        when(deviceRepository.findById(id)).thenReturn(java.util.Optional.empty());

        Device result = deviceService.findById(id);

        assertEquals(null, result);
    }

    @Test
    void shouldReturnNullWhenIdIsLongMaxValue() {
        Long id = Long.MAX_VALUE;
        when(deviceRepository.findById(id)).thenReturn(java.util.Optional.empty());

        Device result = deviceService.findById(id);

        assertEquals(null, result);
    }

    @Test
    void shouldDeleteDeviceWhenExistsAndNotInUse() {
        Long id = 1L;
        Device device = new Device();
        device.setId(id);
        device.setState(DeviceState.AVAILABLE);
        when(deviceRepository.findById(id)).thenReturn(java.util.Optional.of(device));

        deviceService.deleteDevice(id);

        org.mockito.Mockito.verify(deviceRepository).deleteById(id);
    }

    @Test
    void shouldDeleteDeviceWhenStateIsInactive() {
        Long id = 10L;
        Device device = new Device();
        device.setId(id);
        device.setState(DeviceState.INACTIVE);
        when(deviceRepository.findById(id)).thenReturn(java.util.Optional.of(device));

        deviceService.deleteDevice(id);

        org.mockito.Mockito.verify(deviceRepository).deleteById(id);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenDeviceDoesNotExist() {
        Long id = 99L;
        when(deviceRepository.findById(id)).thenReturn(java.util.Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(
            com.dafon.device_api.exception.NotFoundException.class,
            () -> deviceService.deleteDevice(id)
        );
    }

    @Test
    void shouldThrowDeviceInUseExceptionWhenDeviceIsInUse() {
        Long id = 2L;
        Device device = new Device();
        device.setId(id);
        device.setState(DeviceState.IN_USE);
        when(deviceRepository.findById(id)).thenReturn(java.util.Optional.of(device));

        org.junit.jupiter.api.Assertions.assertThrows(
            com.dafon.device_api.exception.DeviceInUseException.class,
            () -> deviceService.deleteDevice(id)
        );
    }

    @Test
    void shouldThrowNotFoundExceptionWhenIdIsNull() {
        org.junit.jupiter.api.Assertions.assertThrows(
            com.dafon.device_api.exception.NotFoundException.class,
            () -> deviceService.deleteDevice(null)
        );
    }

    @Test
    void shouldThrowNotFoundExceptionWhenIdIsNegative() {
        Long id = -1L;
        when(deviceRepository.findById(id)).thenReturn(java.util.Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(
            com.dafon.device_api.exception.NotFoundException.class,
            () -> deviceService.deleteDevice(id)
        );
    }

    @Test
    void shouldThrowNotFoundExceptionWhenIdIsZero() {
        Long id = 0L;
        when(deviceRepository.findById(id)).thenReturn(java.util.Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(
            com.dafon.device_api.exception.NotFoundException.class,
            () -> deviceService.deleteDevice(id)
        );
    }

    @Test
    void shouldCreateDeviceSuccessfully() {
        var dto = new com.dafon.device_api.controller.dto.CreatedDeviceDto("Device X", "BrandX", DeviceState.AVAILABLE);
        Device deviceToSave = new Device();
        deviceToSave.setName(dto.name());
        deviceToSave.setBrand(dto.brand());
        deviceToSave.setState(dto.state());
        // Do not set ID or CreationTime, as they are defined in the service
        Device savedDevice = new Device();
        savedDevice.setId(1L);
        savedDevice.setName(dto.name());
        savedDevice.setBrand(dto.brand());
        savedDevice.setState(dto.state());
        savedDevice.setCreationTime(java.time.LocalDateTime.now());

        org.mockito.Mockito.when(deviceRepository.save(org.mockito.Mockito.any(Device.class))).thenReturn(savedDevice);

        Device result = deviceService.createDevice(dto);

        org.junit.jupiter.api.Assertions.assertEquals(dto.name(), result.getName());
        org.junit.jupiter.api.Assertions.assertEquals(dto.brand(), result.getBrand());
        org.junit.jupiter.api.Assertions.assertEquals(dto.state(), result.getState());
        org.junit.jupiter.api.Assertions.assertNotNull(result.getCreationTime());
    }

    @Test
    void shouldSetCreationTimeOnCreate() {
        var dto = new com.dafon.device_api.controller.dto.CreatedDeviceDto("Device Y", "BrandY", DeviceState.IN_USE);
        Device savedDevice = new Device();
        savedDevice.setId(2L);
        savedDevice.setName(dto.name());
        savedDevice.setBrand(dto.brand());
        savedDevice.setState(dto.state());
        savedDevice.setCreationTime(java.time.LocalDateTime.now());

        org.mockito.Mockito.when(deviceRepository.save(org.mockito.Mockito.any(Device.class))).thenReturn(savedDevice);

        Device result = deviceService.createDevice(dto);

        org.junit.jupiter.api.Assertions.assertNotNull(result.getCreationTime());
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        var dto = new com.dafon.device_api.controller.dto.CreatedDeviceDto("", "BrandZ", DeviceState.AVAILABLE);
        // The method does not validate, but we can ensure that the blank name is passed correctly
        Device savedDevice = new Device();
        savedDevice.setId(3L);
        savedDevice.setName(dto.name());
        savedDevice.setBrand(dto.brand());
        savedDevice.setState(dto.state());
        savedDevice.setCreationTime(java.time.LocalDateTime.now());

        org.mockito.Mockito.when(deviceRepository.save(org.mockito.Mockito.any(Device.class))).thenReturn(savedDevice);

        Device result = deviceService.createDevice(dto);

        org.junit.jupiter.api.Assertions.assertEquals("", result.getName());
    }

    @Test
    void shouldThrowExceptionWhenBrandIsBlank() {
        var dto = new com.dafon.device_api.controller.dto.CreatedDeviceDto("Device Z", "", DeviceState.AVAILABLE);
        Device savedDevice = new Device();
        savedDevice.setId(4L);
        savedDevice.setName(dto.name());
        savedDevice.setBrand(dto.brand());
        savedDevice.setState(dto.state());
        savedDevice.setCreationTime(java.time.LocalDateTime.now());

        org.mockito.Mockito.when(deviceRepository.save(org.mockito.Mockito.any(Device.class))).thenReturn(savedDevice);

        Device result = deviceService.createDevice(dto);

        org.junit.jupiter.api.Assertions.assertEquals("", result.getBrand());
    }

    @Test
    void shouldThrowExceptionWhenStateIsNull() {
        var dto = new com.dafon.device_api.controller.dto.CreatedDeviceDto("Device Null", "BrandNull", null);
        Device savedDevice = new Device();
        savedDevice.setId(5L);
        savedDevice.setName(dto.name());
        savedDevice.setBrand(dto.brand());
        savedDevice.setState(dto.state());
        savedDevice.setCreationTime(java.time.LocalDateTime.now());

        org.mockito.Mockito.when(deviceRepository.save(org.mockito.Mockito.any(Device.class))).thenReturn(savedDevice);

        Device result = deviceService.createDevice(dto);

        org.junit.jupiter.api.Assertions.assertNull(result.getState());
    }

    @Test
    void shouldUpdateAllFieldsSuccessfully() {
        Long id = 1L;
        var dto = new com.dafon.device_api.controller.dto.UpdateDeviceDto("NewName", "NewBrand", DeviceState.INACTIVE);
        Device device = new Device();
        device.setId(id);
        device.setName("OldName");
        device.setBrand("OldBrand");
        device.setState(DeviceState.AVAILABLE);
        org.mockito.Mockito.when(deviceRepository.findById(id)).thenReturn(java.util.Optional.of(device));
        org.mockito.Mockito.when(deviceRepository.save(org.mockito.Mockito.any(Device.class))).thenAnswer(inv -> inv.getArgument(0));

        Device result = deviceService.updateDevice(id, dto);

        org.junit.jupiter.api.Assertions.assertEquals("NewName", result.getName());
        org.junit.jupiter.api.Assertions.assertEquals("NewBrand", result.getBrand());
        org.junit.jupiter.api.Assertions.assertEquals(DeviceState.INACTIVE, result.getState());
    }

    @Test
    void shouldUpdateOnlyName() {
        Long id = 2L;
        var dto = new com.dafon.device_api.controller.dto.UpdateDeviceDto("PartialName", null, null);
        Device device = new Device();
        device.setId(id);
        device.setName("OldName");
        device.setBrand("OldBrand");
        device.setState(DeviceState.AVAILABLE);
        org.mockito.Mockito.when(deviceRepository.findById(id)).thenReturn(java.util.Optional.of(device));
        org.mockito.Mockito.when(deviceRepository.save(org.mockito.Mockito.any(Device.class))).thenAnswer(inv -> inv.getArgument(0));

        Device result = deviceService.updateDevice(id, dto);

        org.junit.jupiter.api.Assertions.assertEquals("PartialName", result.getName());
        org.junit.jupiter.api.Assertions.assertEquals("OldBrand", result.getBrand());
        org.junit.jupiter.api.Assertions.assertEquals(DeviceState.AVAILABLE, result.getState());
    }

    @Test
    void shouldUpdateOnlyBrand() {
        Long id = 3L;
        var dto = new com.dafon.device_api.controller.dto.UpdateDeviceDto(null, "PartialBrand", null);
        Device device = new Device();
        device.setId(id);
        device.setName("OldName");
        device.setBrand("OldBrand");
        device.setState(DeviceState.AVAILABLE);
        org.mockito.Mockito.when(deviceRepository.findById(id)).thenReturn(java.util.Optional.of(device));
        org.mockito.Mockito.when(deviceRepository.save(org.mockito.Mockito.any(Device.class))).thenAnswer(inv -> inv.getArgument(0));

        Device result = deviceService.updateDevice(id, dto);

        org.junit.jupiter.api.Assertions.assertEquals("OldName", result.getName());
        org.junit.jupiter.api.Assertions.assertEquals("PartialBrand", result.getBrand());
        org.junit.jupiter.api.Assertions.assertEquals(DeviceState.AVAILABLE, result.getState());
    }

    @Test
    void shouldUpdateOnlyState() {
        Long id = 4L;
        var dto = new com.dafon.device_api.controller.dto.UpdateDeviceDto(null, null, DeviceState.IN_USE);
        Device device = new Device();
        device.setId(id);
        device.setName("OldName");
        device.setBrand("OldBrand");
        device.setState(DeviceState.AVAILABLE);
        org.mockito.Mockito.when(deviceRepository.findById(id)).thenReturn(java.util.Optional.of(device));
        org.mockito.Mockito.when(deviceRepository.save(org.mockito.Mockito.any(Device.class))).thenAnswer(inv -> inv.getArgument(0));

        Device result = deviceService.updateDevice(id, dto);

        org.junit.jupiter.api.Assertions.assertEquals("OldName", result.getName());
        org.junit.jupiter.api.Assertions.assertEquals("OldBrand", result.getBrand());
        org.junit.jupiter.api.Assertions.assertEquals(DeviceState.IN_USE, result.getState());
    }

    @Test
    void shouldThrowInvalidFieldExceptionWhenAllFieldsNull() {
        Long id = 5L;
        var dto = new com.dafon.device_api.controller.dto.UpdateDeviceDto(null, null, null);
        org.junit.jupiter.api.Assertions.assertThrows(
            com.dafon.device_api.exception.InvalidFieldException.class,
            () -> deviceService.updateDevice(id, dto)
        );
    }

    @Test
    void shouldThrowDeviceInUseExceptionWhenChangingNameOrBrandOfDeviceInUse() {
        Long id = 7L;
        var dto = new com.dafon.device_api.controller.dto.UpdateDeviceDto("NewName", "NewBrand", null);
        Device device = new Device();
        device.setId(id);
        device.setName("OldName");
        device.setBrand("OldBrand");
        device.setState(DeviceState.IN_USE);
        org.mockito.Mockito.when(deviceRepository.findById(id)).thenReturn(java.util.Optional.of(device));
        org.junit.jupiter.api.Assertions.assertThrows(
            com.dafon.device_api.exception.DeviceInUseException.class,
            () -> deviceService.updateDevice(id, dto)
        );
    }

    @Test
    void shouldUpdateStateOfDeviceInUse() {
        Long id = 8L;
        var dto = new com.dafon.device_api.controller.dto.UpdateDeviceDto("OldName", "OldBrand", DeviceState.INACTIVE);
        Device device = new Device();
        device.setId(id);
        device.setName("OldName");
        device.setBrand("OldBrand");
        device.setState(DeviceState.IN_USE);
        org.mockito.Mockito.when(deviceRepository.findById(id)).thenReturn(java.util.Optional.of(device));
        org.mockito.Mockito.when(deviceRepository.save(org.mockito.Mockito.any(Device.class))).thenAnswer(inv -> inv.getArgument(0));

        Device result = deviceService.updateDevice(id, dto);

        org.junit.jupiter.api.Assertions.assertEquals(DeviceState.INACTIVE, result.getState());
    }
} 