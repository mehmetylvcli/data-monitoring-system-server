package com.dataMonitoringSystemServer.serviceImpl;


import com.dataMonitoringSystemServer.entities.User;
import com.dataMonitoringSystemServer.entities.UserDetailsImpl;
import com.dataMonitoringSystemServer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService ;

    @Override
    public UserDetails loadUserByUsername(String  userName) throws UsernameNotFoundException {
       // return new MyUserDetails(s);
        Optional<User> user = userService.getUser(userName) ;
        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));

        // Role leri şimdilik okumuyor boş döndürüyorum.  04.01.2020
        List<String> roles = new ArrayList<>() ;    // bu satır roller döndürlmek istendğinde kapatlıp aşağıdaki 2 satır açılacak
        //  List<UserRole> userRoles = userService.getRolesOfUser(userName) ;
      //  List<String> roles = userRoles.stream().map( userRole -> "ROLE_"+ userRole.getRole().getRoleName()).collect(Collectors.toList());

        System.out.println("UserName : " + userName );


        /*
        List<String> roles = userRoles.stream().map( userRole -> {
            return userRole.getRole().getRoleName() ;
        }).collect(Collectors.toList());
         */

        return new UserDetailsImpl(user.get());

    }
}
