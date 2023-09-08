package com.dataMonitoringSystemServer.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


@Entity
@Table(name = "instant_values")
@Data
public class InstantValue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;
    @Column(name = "value")
    private Double value;
    @Column(name = "unit")
    private String unit;
    @Column(name = "point_status")
    private Long pointStatus;
    @Column(name = "inserted_datetime")
    private Timestamp insertedDatetime;
}
