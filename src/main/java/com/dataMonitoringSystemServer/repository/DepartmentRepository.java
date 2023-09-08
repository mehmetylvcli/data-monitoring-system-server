package com.dataMonitoringSystemServer.repository;

import com.dataMonitoringSystemServer.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findAllByUserId(Long userId);
}
