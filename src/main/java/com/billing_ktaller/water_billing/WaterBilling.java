package com.billing_ktaller.water_billing;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
public class WaterBilling {
    @GetMapping("/")
    public String index() {
        return "Welcome to Water Billing!";
    }
}
