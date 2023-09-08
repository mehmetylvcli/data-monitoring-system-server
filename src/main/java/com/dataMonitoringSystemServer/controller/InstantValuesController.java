package com.dataMonitoringSystemServer.controller;

import com.dataMonitoringSystemServer.dto.ConsumptionComparisonDto;
import com.dataMonitoringSystemServer.dto.ResponseDto;
import com.dataMonitoringSystemServer.dto.ResultCodes;
import com.dataMonitoringSystemServer.dto.TotalConsumptionDto;
import com.dataMonitoringSystemServer.entities.Department;
import com.dataMonitoringSystemServer.entities.Device;
import com.dataMonitoringSystemServer.entities.InstantValue;
import com.dataMonitoringSystemServer.repository.DepartmentRepository;
import com.dataMonitoringSystemServer.repository.DeviceRepository;
import com.dataMonitoringSystemServer.repository.InstantValuesRepository;
import com.dataMonitoringSystemServer.task.InstantValuesTask;
import com.dataMonitoringSystemServer.util.Helper;
import com.dataMonitoringSystemServer.util.ShowErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@Transactional
//@CrossOrigin(origins = "*")
@RequestMapping(value = "/instant_values")
public class InstantValuesController {

    @Autowired
    InstantValuesRepository instantValuesRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    InstantValuesTask instantValuesTask;


    @GetMapping("/department/{departmentId}")
    ResponseEntity<?> getValuesByDepartmentId(@PathVariable Long departmentId) {
        ResponseDto responseDto = new ResponseDto();


        List<InstantValue> instantValueList = instantValuesRepository.findTop200ByDepartmentIdOrderByIdDesc(departmentId);

        responseDto.setResponseBody(instantValueList);

        Helper.fillResponse(responseDto, ResultCodes.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/consumption_comparison/user/{userId}")
    ResponseEntity<?> getConsumptionComparisonByUserId(@PathVariable Long userId) {
        ResponseDto responseDto = new ResponseDto();

        List<Department> departmentList = departmentRepository.findAllByUserId(userId);

        List<ConsumptionComparisonDto> departmentsConsumption = new ArrayList<>();
        for (Department department :
                departmentList) {


            List<InstantValue> instantValueList = instantValuesRepository.findAllByDepartmentId(department.getId());

            ConsumptionComparisonDto consumptionComparisonDto = new ConsumptionComparisonDto();

            consumptionComparisonDto.setDepartmentConsumption(instantValueList.stream()
                    .map(x -> x.getValue())
                    .reduce((double) 0, Double::sum));

            consumptionComparisonDto.setDepartmentName(instantValueList.get(0).getDepartment().getName());

            consumptionComparisonDto.setUnit(instantValueList.get(0).getUnit());

            departmentsConsumption.add(consumptionComparisonDto);

        }


        responseDto.setResponseBody(departmentsConsumption);


        Helper.fillResponse(responseDto, ResultCodes.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/total_consumption/department/{departmentId}")
    ResponseEntity<?> getTotalConsumptionByDepartmentId(@PathVariable Long departmentId) {
        ResponseDto responseDto = new ResponseDto();


        List<InstantValue> instantValueList = instantValuesRepository.findAllByDepartmentId(departmentId);

        Double sum = instantValueList.stream()
                .map(x -> x.getValue())
                .reduce((double) 0, Double::sum);

        List<TotalConsumptionDto> response = new ArrayList<>();

        TotalConsumptionDto totalConsumption = new TotalConsumptionDto();

        totalConsumption.setTotalConsumption(sum);
        totalConsumption.setUnit(instantValueList.get(0).getUnit());
        response.add(totalConsumption);


        responseDto.setResponseBody(response);

        Helper.fillResponse(responseDto, ResultCodes.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    @GetMapping("/total_consumption/user/{userId}")
    ResponseEntity<?> getTotalConsumptionByUserId(@PathVariable Long userId) {
        ResponseDto responseDto = new ResponseDto();

        List<Department> departmentList = departmentRepository.findAllByUserId(userId);

        Double sum = 0D;
        String unit = "";
        for (Department department :
                departmentList) {


            List<InstantValue> instantValueList = instantValuesRepository.findAllByDepartmentId(department.getId());

            sum += instantValueList.stream()
                    .map(x -> x.getValue())
                    .reduce((double) 0, Double::sum);

            unit = instantValueList.get(0).getUnit();


        }

        List<TotalConsumptionDto> response = new ArrayList<>();

        TotalConsumptionDto totalConsumption = new TotalConsumptionDto();

        totalConsumption.setTotalConsumption(sum);
        totalConsumption.setUnit(unit);

        response.add(totalConsumption);


        responseDto.setResponseBody(response);


        Helper.fillResponse(responseDto, ResultCodes.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    //Instant unhealthy data generation with API request
    @GetMapping("/create_value/unhealthy_value/department/{departmentId}/device/{deviceId}")
    public ResponseEntity<?> createUnhealhtyValueByDepartmentIdAndDeviceId(@PathVariable Long departmentId, @PathVariable Long deviceId) {
        ResponseDto responseDto = new ResponseDto<>();

        try {


            Department department = departmentRepository.findById(departmentId).orElseThrow(EntityNotFoundException::new);
            Device device = deviceRepository.findById(deviceId).orElseThrow(EntityNotFoundException::new);
            Timestamp now;


            if (instantValuesRepository.findFirstByOrderByIdDesc() == null) {
                now = new Timestamp(System.currentTimeMillis());
            } else {
                now = instantValuesRepository.findFirstByOrderByIdDesc().getInsertedDatetime();
                now.setTime(now.getTime() + TimeUnit.MINUTES.toMillis(1));
            }


            InstantValue instantValue = new InstantValue();


            instantValue = instantValuesTask.createUnhealthyValue(device, now, departmentId);
            if (instantValue != null) {
                instantValuesRepository.save(instantValue);
            }

            List<InstantValue> instantValueList = new ArrayList<>();

            instantValueList.add(instantValue);

            responseDto.setResponseBody(instantValueList);
        } catch (EntityNotFoundException e) {
            throw new ShowErrorException("Department or Device Informations are Incorrect");
        }

        // return değiştirilecek**

        Helper.fillResponse(responseDto, ResultCodes.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
