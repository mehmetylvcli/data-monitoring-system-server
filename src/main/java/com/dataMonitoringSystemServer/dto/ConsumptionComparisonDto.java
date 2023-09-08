package com.dataMonitoringSystemServer.dto;

public class ConsumptionComparisonDto {

    private Double departmentTotalConsumption;

    private String unit;

    private String departmentName;

    public Double getDepartmentConsumption() {
        return departmentTotalConsumption;
    }

    public Double setDepartmentConsumption(Double totalConsumption) {
        this.departmentTotalConsumption = totalConsumption;
        return this.departmentTotalConsumption;
    }


    public String getDepartmentName() {
        return departmentName;
    }

    public String setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
        return this.departmentName;
    }


    public String getUnit() {
        return unit;
    }

    public String setUnit(String unit) {
        this.unit = unit;
        return this.unit;
    }
}
