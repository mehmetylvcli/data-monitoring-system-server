package com.dataMonitoringSystemServer.controller;

import com.dataMonitoringSystemServer.dto.*;
import com.dataMonitoringSystemServer.entities.User;
import com.dataMonitoringSystemServer.repository.UserRepository;
import com.dataMonitoringSystemServer.service.UserService;
import com.dataMonitoringSystemServer.util.Helper;
import com.dataMonitoringSystemServer.util.ShowErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
//@CrossOrigin(origins = "*")
@RequestMapping(value = "/auth")
public class AuthController {


    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserService userService;

    @Autowired
    UserDetailsService userDetailsService;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDto loginRequest) {
        ResponseDto responseDto = new ResponseDto<>();

        UserDetails user = userDetailsService.loadUserByUsername(loginRequest.getUsername());


        if (userRepository.existsByUsername(loginRequest.getUsername())) {
            if(encoder.matches(loginRequest.getPassword(),user.getPassword() )){
                List<UserDetails> userList = new ArrayList<>();
                userList.add(user);
                responseDto.setResponseBody(userList);
            }else {
                throw new ShowErrorException("Password incorrect");
            }

        }

        Helper.fillResponse(responseDto, ResultCodes.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

//    @ApiOperation(value = "Create new User ", notes = "Create new User for Signup", response = User.class)
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        ResponseDto<User> responseDto = new ResponseDto<>();
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ShowErrorException("User already exists");
        }
        user.setPassword(encoder.encode(user.getPassword()));


        User savedUser = userService.signUp(user);
        List<User> userList = new ArrayList<>();
        userList.add(savedUser);
        responseDto.setResponseBody(userList);

        Helper.fillResponse(responseDto, ResultCodes.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

//    @ApiOperation(value = "Change password of USer ", notes = "Only username and password field in request", response = User.class)
    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody User user) {
        ResponseDto<User> responseDto = new ResponseDto<>();
        user.setPassword(encoder.encode(user.getPassword())); // encode password
        userService.changePassword(user);

        Helper.fillResponse(responseDto, ResultCodes.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }


}
