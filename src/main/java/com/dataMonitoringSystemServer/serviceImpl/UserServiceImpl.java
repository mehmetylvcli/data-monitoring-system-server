package com.dataMonitoringSystemServer.serviceImpl;

import com.dataMonitoringSystemServer.entities.*;
import com.dataMonitoringSystemServer.repository.UserRepository;
import com.dataMonitoringSystemServer.service.UserService;
import com.dataMonitoringSystemServer.util.ShowErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
//@PropertySource("classpath:config.properties")
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;


    @Override
    public User signUp(User user) {
        user.setCreatedDateTime(new Timestamp(System.currentTimeMillis()));
        user.setUpdatedDateTime(new Timestamp(System.currentTimeMillis()));
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }



    @Override
    public void changePassword(User user) {
        Optional<User> currentUser = getUser(user.getUsername());
        currentUser.orElseThrow(() -> new ShowErrorException("User not found"));
        currentUser.get().setPassword(user.getPassword());
        currentUser.get().setUpdatedDateTime(new Timestamp(System.currentTimeMillis()));
        userRepository.save(currentUser.get());

    }

}
