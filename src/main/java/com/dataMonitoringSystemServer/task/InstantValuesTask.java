package com.dataMonitoringSystemServer.task;


import com.dataMonitoringSystemServer.dto.ResponseDto;
import com.dataMonitoringSystemServer.dto.ResultCodes;
import com.dataMonitoringSystemServer.entities.Device;
import com.dataMonitoringSystemServer.entities.InstantValue;
import com.dataMonitoringSystemServer.repository.DepartmentRepository;
import com.dataMonitoringSystemServer.repository.DeviceRepository;
import com.dataMonitoringSystemServer.repository.InstantValuesRepository;
import com.dataMonitoringSystemServer.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

//Instant data generation
@Service
@Transactional
public class InstantValuesTask {

    @Autowired
    InstantValuesRepository instantValuesRepository;

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    // Random data generation function that runs every 5 seconds
//    @Scheduled(cron = "0/5 * * * * *")
    public void createValueEveryMinute() {

        Timestamp now;

        List<Long> departments = new ArrayList<>();

//        departments.add(1L);
        departments.add(25L);

        for (Long departmentId : departments) {
            if (departmentId != null) {


                List<Device> deviceList = deviceRepository.findAll();
                if (instantValuesRepository.findFirstByOrderByIdDesc() == null) {
                    now = new Timestamp(System.currentTimeMillis());
                } else {
                    now = instantValuesRepository.findFirstByOrderByIdDesc().getInsertedDatetime();
                    now.setTime(now.getTime() + TimeUnit.MINUTES.toMillis(1));
                }


                for (Device device : deviceList) {
                    Random r = new Random();
                    double random = 0 + r.nextDouble() * (100);
                    InstantValue instantValue = new InstantValue();

                    if (random > 80) {
                        instantValue = createUnhealthyValue(device, now, departmentId);
                    } else if (random <= 80 && random >= 20) {
                        instantValue = createHealthyValue(device, now, departmentId);
                    } else {
                        instantValue = createOfflineValue(device, now, departmentId);
                    }

//                    instantValue = createOfflineValue(device, now, departmentId);

                    if (instantValue != null) {
                        instantValuesRepository.save(instantValue);
                    }

                }
            }

        }


    }

    //Instant healthy data generation with API request
    public InstantValue createHealthyValue(Device device, Timestamp now, Long departmentId) {

        InstantValue instantValue = new InstantValue();


        System.out.println(device);
        if (device.getConsumptionValue() != null) {


            Double threshholdValue = device.getConsumptionValue();
            Double threshholdValueMin = threshholdValue - (threshholdValue * 25 / 100);
            Double threshholdValueMax = threshholdValue + (threshholdValue * 25 / 100);

            Random r = new Random();
            double random = threshholdValueMin + r.nextDouble() * (threshholdValueMax - threshholdValueMin);

//                String randomValue = new DecimalFormat("##.##").format(random);


            instantValue.setValue(random);
            instantValue.setDepartment(departmentRepository.getOne(departmentId));
            instantValue.setDevice(device);
            instantValue.setName(device.getDev_name() + " Tüketim");
            instantValue.setUnit(device.getUnit());
            instantValue.setInsertedDatetime(now);
            instantValue.setPointStatus(1L);

            return instantValue;
        }

        return null;
    }

    //Instant unhealthy data generation with API request
    public InstantValue createUnhealthyValue(Device device, Timestamp now, Long departmentId) {


        InstantValue instantValue = new InstantValue();


        System.out.println(device);
        if (device.getConsumptionValue() != null) {


            Double threshholdValue = device.getConsumptionValue();
            Double threshholdValueMin = threshholdValue - (threshholdValue * 100 / 100);
            Double threshholdValueSecondMin = threshholdValue - (threshholdValue * 50 / 100);
            Double threshholdValueMax = threshholdValue + (threshholdValue * 100 / 100);
            Double threshholdValueSecondMax = threshholdValue + (threshholdValue * 50 / 100);


            Random r = new Random();
            double random = 0;


            Random r1 = new Random();
            double random1 = 0 + r.nextDouble() * (100);

            if (random1 > 50) {
                random = threshholdValueSecondMax + r.nextDouble() * Math.abs(threshholdValueMax - threshholdValueSecondMax);
            } else {
                random = threshholdValueMin + r.nextDouble() * Math.abs(threshholdValueSecondMin - threshholdValueMin);
            }
            //                String randomValue = new DecimalFormat("##.##").format(random);


            instantValue.setValue(random);
            instantValue.setDepartment(departmentRepository.getOne(departmentId));
            instantValue.setDevice(device);
            instantValue.setName(device.getDev_name() + " Tüketim");
            instantValue.setUnit(device.getUnit());
            instantValue.setInsertedDatetime(now);
            instantValue.setPointStatus(2L);

            return instantValue;
        }

        return null;
    }


    //Instant offline data generation with API request
    public InstantValue createOfflineValue(Device device, Timestamp now, Long departmentId) {


        InstantValue instantValue = new InstantValue();


        System.out.println(device);
        if (device.getConsumptionValue() != null) {


            instantValue.setValue(0D);
            instantValue.setDepartment(departmentRepository.getOne(departmentId));
            instantValue.setDevice(device);
            instantValue.setName(device.getDev_name() + " Tüketim");
            instantValue.setUnit(device.getUnit());
            instantValue.setInsertedDatetime(now);
            instantValue.setPointStatus(0L);

            return instantValue;
        }

        return null;
    }
}


