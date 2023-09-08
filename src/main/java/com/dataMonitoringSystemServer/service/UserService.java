package com.dataMonitoringSystemServer.service;

import com.dataMonitoringSystemServer.entities.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    User signUp(User user);

    Optional<User> getUser(String username);

    List<User> getUsers();


    void changePassword(User user);


}
