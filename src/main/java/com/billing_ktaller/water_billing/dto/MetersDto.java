package com.billing_ktaller.water_billing.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MetersDto {
    // Getters and setters
    private String meterNumber;
    private Long customer;
    private double locationLat;
    private double locationLng;
    private Long status;
    private Long meterType;
}
