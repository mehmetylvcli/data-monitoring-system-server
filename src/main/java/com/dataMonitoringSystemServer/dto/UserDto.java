package com.dataMonitoringSystemServer.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
@Data
public class UserDto implements Serializable {
	private Long id;
	private Long customerId ;
	private String  username;
	private String fullName;
	private String profilePicture;
	private String  email;
	private String phoneNumber;
	private String password;
	private Timestamp createdDateTime;
	private Timestamp updatedDateTime ;

}
