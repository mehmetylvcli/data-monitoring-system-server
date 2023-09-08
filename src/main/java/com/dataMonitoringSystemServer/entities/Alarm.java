package com.dataMonitoringSystemServer.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "alarm_list")
@Data
public class Alarm {

        private static final long serialVersionUID = 1L;
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", updatable = false, nullable = false)
        private Long id;
        @Column(name = "alarm_name")
        private String alarmName;
        @Column(name = "alarm_desc")
        private String alarmDesc;
        @JsonIgnore
        @ManyToOne
        @JoinColumn(name = "department_id")
        private Department department;
//        @JsonIgnore
        @ManyToOne
        @JoinColumn(name = "device_id")
        private Device device;
        @Column(name = "problematic_value")
        private Double problematicValue;
        @Column(name = "user_id")
        private Long userId;
        @Column(name = "created_datetime")
        private Timestamp createdDatetime;
        @Column(name = "is_active")
        private boolean isActive;

}
