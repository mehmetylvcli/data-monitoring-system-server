package com.dataMonitoringSystemServer.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "device_list")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Column(name = "dev_name")
    private String dev_name;
    @Column(name = "dev_desc")
    private String dev_desc;
    @Column(name = "icon_url")
    private String icon_url;
    @Column(name = "consumption_value")
    private Double consumptionValue;
    @Column(name = "unit")
    private String unit;
}
