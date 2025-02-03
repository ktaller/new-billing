package com.billing_ktaller.water_billing.dto;

import lombok.Getter;
import lombok.Setter;

public class MetersDto {
    // Getters and setters
    @Setter
    @Getter
    private String meterNumber;
    @Setter
    @Getter
    private Long customer;
    @Setter
    @Getter
    private double locationLat;
    @Setter
    @Getter
    private double locationLng;
    @Setter
    @Getter
    private Long status;
    @Setter
    @Getter
    private Long meterType;
}
