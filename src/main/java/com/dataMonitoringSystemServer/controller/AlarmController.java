package com.dataMonitoringSystemServer.controller;


import com.dataMonitoringSystemServer.dto.AlarmDto;
import com.dataMonitoringSystemServer.dto.ResponseDto;
import com.dataMonitoringSystemServer.dto.ResultCodes;
import com.dataMonitoringSystemServer.entities.Alarm;
import com.dataMonitoringSystemServer.repository.AlarmRepository;
import com.dataMonitoringSystemServer.repository.DepartmentRepository;
import com.dataMonitoringSystemServer.repository.DeviceRepository;
import com.dataMonitoringSystemServer.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping(value = "/alarms")
public class AlarmController {

    @Autowired
    AlarmRepository alarmRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    DeviceRepository deviceRepository;


    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAlarmsByUserId(@PathVariable Long userId) {
        ResponseDto responseDto = new ResponseDto<>();

        List<Alarm> alarmList = alarmRepository.getAllByUserId(userId);

        responseDto.setResponseBody(alarmList);


        Helper.fillResponse(responseDto, ResultCodes.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/save_alarm")
    public ResponseEntity<?> saveAlarm(@RequestBody AlarmDto alarmDto) {
        ResponseDto responseDto = new ResponseDto<>();

        List<Alarm> alarmList = new ArrayList<>();


        List<Alarm> alarms = alarmRepository.getAlarmByDepartmentAndDevice(departmentRepository.getOne(alarmDto.getDepartmentId()), deviceRepository.getOne(alarmDto.getDeviceId()));
        //set active property to false if alarms are defined for customer in db
        if (alarms != null) {
            for (Alarm alarm :
                    alarms) {
                if(alarm.isActive() == true){
                    alarm.setActive(false);
                    alarmList.add(alarm);
                }
            }
        }
        Alarm alarm = new Alarm();

        alarm.setAlarmName(alarmDto.getAlarmName());
        alarm.setAlarmDesc(alarmDto.getAlarmDesc());
        alarm.setUserId(alarmDto.getUserId());
        alarm.setDepartment(departmentRepository.getOne(alarmDto.getDepartmentId()));
        alarm.setDevice(deviceRepository.getOne(alarmDto.getDeviceId()));
        alarm.setProblematicValue(alarmDto.getProblematicValue());
        alarm.setCreatedDatetime(new Timestamp(System.currentTimeMillis()));
        alarm.setActive(true);

        alarmList.add(alarm);

        alarmRepository.saveAll(alarmList);

        responseDto.setResponseBody(alarmList);

        Helper.fillResponse(responseDto, ResultCodes.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
