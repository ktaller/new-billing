package com.billing_ktaller.water_billing.controllers;

import com.billing_ktaller.water_billing.dto.MeterNumberDto;
import com.billing_ktaller.water_billing.dto.MeterReadingsDto;
import com.billing_ktaller.water_billing.dto.MonthlyAccumulatedAmountReportDto;
import com.billing_ktaller.water_billing.dto.MonthlyConsumptionReportDto;
import com.billing_ktaller.water_billing.dto.YearDto;
import com.billing_ktaller.water_billing.models.MeterReadings;
import com.billing_ktaller.water_billing.models.Meters;
import com.billing_ktaller.water_billing.repositories.MeterReadingRepository;
import com.billing_ktaller.water_billing.repositories.MeterRepository;
import jakarta.validation.Valid;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/meter-readings")
public class MeterReadingController {
    @Autowired
    private MeterReadingRepository meterReadingRepository;

    @Autowired
    private MeterRepository meterRepository;

    // POST method to create a new meter reading
    @PostMapping("/create")
    public ResponseEntity<String> createMeterReading(@Valid @RequestBody MeterReadingsDto meterReadingsDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
        }

        // Check if the meter exists before saving
        if (meterRepository.findByMeterNumber(meterReadingsDto.getMeterNumber()).isEmpty()) {
            return ResponseEntity.badRequest().body("Meter does not exist.");
        }

        Meters meter = meterRepository.findByMeterNumber(meterReadingsDto.getMeterNumber()).get();
        Optional<MeterReadings> optionalPreviousReadings = meterReadingRepository
                .findTopByMeterOrderByCreatedAtDesc(meter);

        // check if the meter reading has been recorded for this month so that it does
        // not get recorded again
        if (optionalPreviousReadings.isPresent()) {
            MeterReadings previousReadings = optionalPreviousReadings.get();

            if (previousReadings.getCreatedAt().atZone(ZoneId.systemDefault()).getMonthValue() == meterReadingsDto
                    .getCurrentDateTime().atZone(ZoneId.systemDefault()).getMonthValue()
                    && previousReadings.getCreatedAt().atZone(ZoneId.systemDefault()).getYear() == meterReadingsDto
                            .getCurrentDateTime().atZone(ZoneId.systemDefault()).getYear()) {
                return ResponseEntity.badRequest().body("Meter reading for this month has already been recorded.");
            }
        }

        float previousReading = optionalPreviousReadings.map(MeterReadings::getCurrentReading).orElse(0F);

        // if current reading is less than previous reading, return error
        if (meterReadingsDto.getCurrentReading() < previousReading) {
            return ResponseEntity.badRequest()
                    .body("Current reading cannot be less than previous reading. Meter integrity error.");
        }

        MeterReadings meterReading = new MeterReadings(meter, previousReading, meterReadingsDto.getCurrentReading());

        meterReadingRepository.save(meterReading);
        /*
         * consumption, rate per unit, amount, standing charger
         * {
         * "consumption": 10.0,
         * "rate": 5.0,
         * "amount": 50.0
         * }
         */
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("consumption", meterReading.getConsumption());
        jsonObject.put("rate", meter.getMeterType().getRate());
        jsonObject.put("amount", meterReading.getAmount());
        return ResponseEntity.ok(jsonObject.toString());
    }

    // get meter readings by meter. the json body will contain the meter id to get
    // the meter readings for
    @PostMapping("/get-by-meter")
    public ResponseEntity<?> getMeterReadingsByMeter(@Valid @RequestBody MeterNumberDto meterNumberDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
        }

        Optional<Meters> meterOptional = meterRepository.findByMeterNumber(meterNumberDto.getMeterNumber());
        if (meterOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Meter does not exist.");
        }

        Meters meterObj = meterOptional.get();

        return ResponseEntity.ok(meterReadingRepository.findByMeterOrderByCreatedAtDesc(meterObj));
    }

    // POST method to update a meter reading
    @PostMapping("/update")
    public ResponseEntity<String> updateMeterReading(@Valid @RequestBody MeterReadings meterReading,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
        }

        // Check if the meter reading exists before updating
        if (!meterReadingRepository.existsById(meterReading.getId())) {
            return ResponseEntity.badRequest().body("Meter reading does not exist.");
        }

        meterReadingRepository.save(meterReading);
        return ResponseEntity.ok("Meter reading updated successfully");
    }

    // POST method to delete a meter reading
    @PostMapping("/delete")
    public ResponseEntity<String> deleteMeterReading(@Valid @RequestBody MeterReadings meterReading,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
        }

        // Check if the meter reading exists before deleting
        if (!meterReadingRepository.existsById(meterReading.getId())) {
            return ResponseEntity.badRequest().body("Meter reading does not exist.");
        }

        meterReadingRepository.delete(meterReading);
        return ResponseEntity.ok("Meter reading deleted successfully");
    }

    @PostMapping("/consumption-report")
    public ResponseEntity<?> getConsumptionReport() {
        List<MonthlyConsumptionReportDto> report = meterReadingRepository.findConsumptionReport();
        return ResponseEntity.ok(report);
    }

    @PostMapping("/revenue-report")
    public ResponseEntity<?> getConsumptionReport(@RequestBody YearDto yearDto) {
        List<MonthlyAccumulatedAmountReportDto> report = meterReadingRepository
                .findAccumulatedAmountReportForYear(yearDto.getYear());
        return ResponseEntity.ok(report);
    }
}
