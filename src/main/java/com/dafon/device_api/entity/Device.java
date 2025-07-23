package com.dafon.device_api.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "devices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "brand")
    private String brand;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private DeviceState state;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

}
