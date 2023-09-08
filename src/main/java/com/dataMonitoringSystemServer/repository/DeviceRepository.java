package com.dataMonitoringSystemServer.repository;

import com.dataMonitoringSystemServer.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {

}
