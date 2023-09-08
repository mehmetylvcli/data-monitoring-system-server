package com.dataMonitoringSystemServer.dto;

public class TotalConsumptionDto {

    private Double totalConsumption;

    private String unit;

    public Double getTotalConsumption() {
        return totalConsumption;
    }

    public Double setTotalConsumption(Double totalConsumption) {
        this.totalConsumption = totalConsumption;
        return this.totalConsumption;
    }


    public String getUnit() {
        return unit;
    }

    public String setUnit(String unit) {
        this.unit = unit;
        return this.unit;
    }

}
