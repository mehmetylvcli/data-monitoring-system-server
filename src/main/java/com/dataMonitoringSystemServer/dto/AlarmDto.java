package com.dataMonitoringSystemServer.dto;

public class AlarmDto {
    private String alarmName;

    private String alarmDesc;

    private Long departmentId;

    private Long deviceId;

    private Double problematicValue;

    private Long userId;

    public String getAlarmName() {
        return alarmName;
    }

    public String getAlarmDesc() {
        return alarmDesc;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public Long getUserId() {
        return userId;
    }
    public Double getProblematicValue() {
        return problematicValue;
    }

}
