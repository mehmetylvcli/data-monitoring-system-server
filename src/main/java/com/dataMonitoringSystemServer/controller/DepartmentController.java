package com.dataMonitoringSystemServer.controller;


import com.dataMonitoringSystemServer.dto.ResponseDto;
import com.dataMonitoringSystemServer.dto.ResultCodes;
import com.dataMonitoringSystemServer.entities.Department;
import com.dataMonitoringSystemServer.entities.User;
import com.dataMonitoringSystemServer.repository.DepartmentRepository;
import com.dataMonitoringSystemServer.repository.UserRepository;
import com.dataMonitoringSystemServer.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping(value = "/departments")
public class DepartmentController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DepartmentRepository departmentRepository;

     @GetMapping("/{userId}")
     ResponseEntity<?> getDepartmensByUserId(@PathVariable Long userId){
         Optional<User> user = userRepository.findById(userId);
         user.orElseThrow(() -> new InvalidParameterException("Kullanıcı bulunamadı!"));

         ResponseDto responseDto = new ResponseDto();

         List<Department> departmentList = departmentRepository.findAllByUserId(userId);

         responseDto.setResponseBody(departmentList);

         Helper.fillResponse(responseDto, ResultCodes.OK, null);
         return new ResponseEntity<>(responseDto, HttpStatus.OK);
     }
}
