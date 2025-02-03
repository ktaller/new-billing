package com.billing_ktaller.water_billing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestDao {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
