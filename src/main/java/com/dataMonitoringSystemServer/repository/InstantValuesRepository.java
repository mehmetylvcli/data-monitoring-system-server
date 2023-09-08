package com.dataMonitoringSystemServer.repository;

import com.dataMonitoringSystemServer.entities.InstantValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InstantValuesRepository extends JpaRepository<InstantValue, Long> {

    List<InstantValue> findAllByDepartmentId(Long departmentId);

    List<InstantValue> findTop200ByDepartmentIdOrderByIdDesc(Long departmentId);

    InstantValue findFirstByOrderByIdDesc();




    @Query(value = "SELECT SUM(value) FROM instant_values iv WHERE iv.department_id = ?1", nativeQuery = true)
    Long getTotalConsumptionByDepartmentId(Long departmentId);

}
