package com.billing_ktaller.water_billing.controllers;

import com.billing_ktaller.water_billing.models.MeterStatuses;
import com.billing_ktaller.water_billing.repositories.MeterStatusRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meter-status")
public class MeterStatusController {
    @Autowired
    private MeterStatusRepository meterStatusRepository;

    // POST method to create a new meter status
    @PostMapping("/create")
    public ResponseEntity<String> createMeterStatus(@Valid @RequestBody MeterStatuses meterStatus,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
        }

        meterStatusRepository.save(meterStatus);
        return ResponseEntity.ok("Meter status created successfully");
    }

    // POST method to retrieve all meter statuses
    @PostMapping("/get")
    public ResponseEntity<?> getAllMeterStatuses() {
        return ResponseEntity.ok(meterStatusRepository.findAll());
    }

    // POST method to update a meter status
    @PostMapping("/update")
    public ResponseEntity<String> updateMeterStatus(@Valid @RequestBody MeterStatuses meterStatus,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
        }

        // Check if the meter status exists before updating
        if (!meterStatusRepository.existsById(meterStatus.getId())) {
            return ResponseEntity.badRequest().body("Meter status does not exist.");
        }

        meterStatusRepository.save(meterStatus);
        return ResponseEntity.ok("Meter status updated successfully");
    }

    // POST method to delete a meter status
    @PostMapping("/delete")
    public ResponseEntity<String> deleteMeterStatus(@Valid @RequestBody MeterStatuses meterStatus,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
        }

        // Check if the meter status exists before deleting
        if (!meterStatusRepository.existsById(meterStatus.getId())) {
            return ResponseEntity.badRequest().body("Meter status does not exist.");
        }

        meterStatusRepository.delete(meterStatus);
        return ResponseEntity.ok("Meter status deleted successfully");
    }
}
