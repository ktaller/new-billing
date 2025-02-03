package com.billing_ktaller.water_billing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyAccumulatedAmountReportDto {
    private int month;
    private double totalAmount;
}
