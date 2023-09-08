package com.dataMonitoringSystemServer.repository;

import com.dataMonitoringSystemServer.entities.Alarm;
import com.dataMonitoringSystemServer.entities.Department;
import com.dataMonitoringSystemServer.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;


@Transactional
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    List<Alarm> getAllByUserId(Long userId);

    public void deleteAlarmById(Long id);

    List<Alarm> getAlarmByDepartmentAndDevice(Department department, Device device);
}
