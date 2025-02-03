package com.billing_ktaller.water_billing.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeterReadingsDto {
    private String meterNumber;
    private float currentReading;
    private LocalDateTime currentDateTime;
}
