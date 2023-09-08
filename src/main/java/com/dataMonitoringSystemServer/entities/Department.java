package com.dataMonitoringSystemServer.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "department_list")
@Data
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "point_desc")
    private String pointDesc;
    @Column(name = "level")
    private Long level;


}
