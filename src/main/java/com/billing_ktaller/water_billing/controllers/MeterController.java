package com.billing_ktaller.water_billing.controllers;

import com.billing_ktaller.water_billing.dto.CustomerIdDto;
import com.billing_ktaller.water_billing.dto.MetersDto;
import com.billing_ktaller.water_billing.models.Customers;
import com.billing_ktaller.water_billing.models.MeterStatuses;
import com.billing_ktaller.water_billing.models.MeterTypes;
import com.billing_ktaller.water_billing.models.Meters;
import com.billing_ktaller.water_billing.repositories.CustomerRepository;
import com.billing_ktaller.water_billing.repositories.MeterRepository;
import com.billing_ktaller.water_billing.repositories.MeterStatusRepository;
import com.billing_ktaller.water_billing.repositories.MeterTypeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/meters")
public class MeterController {
    @Autowired
    private MeterRepository meterRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MeterStatusRepository meterStatusRepository;
    @Autowired
    private MeterTypeRepository meterTypeRepository;

    // POST method to create a new meter
    @PostMapping("/create")
    public ResponseEntity<String> createMeter(@Valid @RequestBody MetersDto metersDto,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
        }

        Optional<Customers> customerOptional = customerRepository.findById(metersDto.getCustomer());
        if (customerOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Customer with ID " + metersDto.getCustomer() + " not found.");
        }

        Optional<MeterStatuses> meterStatusesOptional = meterStatusRepository.findById(metersDto.getStatus());
        if (meterStatusesOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Meter status with ID " + metersDto.getStatus() + " not found.");
        }

        Optional<MeterTypes> meterTypesOptional = meterTypeRepository.findById(metersDto.getMeterType());
        if (meterTypesOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Meter type with ID " + metersDto.getMeterType() + " not found.");
        }

        // Check for duplicate meter number before saving
        if (meterRepository.findByMeterNumber(metersDto.getMeterNumber()).isPresent()) {
            return ResponseEntity.badRequest().body("Meter number already exists.");
        }

        Customers customer = customerOptional.get();
        MeterStatuses meterStatuses = meterStatusesOptional.get();
        MeterTypes meterTypes = meterTypesOptional.get();
        Meters meter = new Meters(metersDto.getMeterNumber(), customer, metersDto.getLocationLat(), metersDto.getLocationLng(), meterStatuses, meterTypes);

        meterRepository.save(meter);
        return ResponseEntity.ok("Meter created successfully");
    }

    // POST method to update a meter
    @PostMapping("/update")
    public ResponseEntity<String> updateMeter(@Valid @RequestBody Meters meter, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
        }

        // Check if the meter exists before updating
        if (!meterRepository.existsById(meter.getId())) {
            return ResponseEntity.badRequest().body("Meter does not exist.");
        }

        meterRepository.save(meter);
        return ResponseEntity.ok("Meter updated successfully");
    }

    // POST method to delete a meter
    @PostMapping("/delete")
    public ResponseEntity<String> deleteMeter(@Valid @RequestBody Meters meter, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
        }

        // Check if the meter exists before deleting
        if (!meterRepository.existsById(meter.getId())) {
            return ResponseEntity.badRequest().body("Meter does not exist.");
        }

        meterRepository.delete(meter);
        return ResponseEntity.ok("Meter deleted successfully");
    }

    // POST method to deactivate a meter
    @PostMapping("/deactivate")
    public ResponseEntity<String> deactivateMeter(@Valid @RequestBody Meters meter, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
        }

        // Check if the meter exists before deactivating
        if (!meterRepository.existsById(meter.getId())) {
            return ResponseEntity.badRequest().body("Meter does not exist.");
        }

        meter.setActive(false);
        meterRepository.save(meter);
        return ResponseEntity.ok("Meter deactivated successfully");
    }

    // POST method to activate a meter
    @PostMapping("/activate")
    public ResponseEntity<String> activateMeter(@Valid @RequestBody Meters meter, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
        }

        // Check if the meter exists before activating
        if (!meterRepository.existsById(meter.getId())) {
            return ResponseEntity.badRequest().body("Meter does not exist.");
        }

        meter.setActive(true);
        meterRepository.save(meter);
        return ResponseEntity.ok("Meter activated successfully");
    }

    // POST method to get all meters
    @PostMapping("")
    public ResponseEntity<List<Meters>> getMeters() {
        return ResponseEntity.ok(meterRepository.findAll());
    }

    // POST method to get meters assigned to a customer
    @PostMapping("/customer")
    public ResponseEntity<List<Meters>> getMetersByCustomer(@Valid @RequestBody CustomerIdDto customerIdDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(List.of());
        }
        
        Optional<Customers> customerOptional = customerRepository.findById(customerIdDto.getId());
        if (customerOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(List.of());
        }

        return ResponseEntity.ok(meterRepository.findByCustomer(customerOptional.get()));
    }
}
