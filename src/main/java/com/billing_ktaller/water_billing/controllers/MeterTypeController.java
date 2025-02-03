package com.billing_ktaller.water_billing.controllers;

import com.billing_ktaller.water_billing.models.MeterTypes;
import com.billing_ktaller.water_billing.repositories.MeterTypeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meter-type")
public class MeterTypeController {
    @Autowired
    private MeterTypeRepository meterTypeRepository;

    // POST method to create a new meter type
    @PostMapping("/create")
    public ResponseEntity<String> createMeterType(@Valid @RequestBody MeterTypes meterType,
                                                  BindingResult bindingResult) {
        System.out.println(meterType);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
        }

        meterTypeRepository.save(meterType);
        return ResponseEntity.ok("Meter type created successfully");
    }

    // POST method to retrieve all meter types
    @PostMapping("/get")
    public ResponseEntity<?> getAllMeterTypes() {
        return ResponseEntity.ok(meterTypeRepository.findAll());
    }

    // POST method to update a meter type
    @PostMapping("/update")
    public ResponseEntity<String> updateMeterType(@Valid @RequestBody MeterTypes meterType,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
        }

        // Check if the meter type exists before updating
        if (!meterTypeRepository.existsById(meterType.getId())) {
            return ResponseEntity.badRequest().body("Meter type does not exist.");
        }

        meterTypeRepository.save(meterType);
        return ResponseEntity.ok("Meter type updated successfully");
    }

    // POST method to delete a meter type
    @PostMapping("/delete")
    public ResponseEntity<String> deleteMeterType(@Valid @RequestBody MeterTypes meterType,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
        }

        // Check if the meter type exists before deleting
        if (!meterTypeRepository.existsById(meterType.getId())) {
            return ResponseEntity.badRequest().body("Meter type does not exist.");
        }

        meterTypeRepository.delete(meterType);
        return ResponseEntity.ok("Meter type deleted successfully");
    }
}
