package com.billing_ktaller.water_billing.auth;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PingResponse {
    private String message;
    private String firstName;
    private String lastName;
}
