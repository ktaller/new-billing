package com.billing_ktaller.water_billing.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Getter
@Entity
public class MeterReadings {
    /**
     * This class is used to store the meter readings of the customer.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Setter
    @ManyToOne
    private Meters meter;
    @Setter
    private float previousReading;
    @Setter
    private float currentReading;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    public MeterReadings() {
    }

    public MeterReadings(Meters meter, float previousReading, float currentReading) {
        this.meter = meter;
        this.previousReading = previousReading;
        this.currentReading = currentReading;
    }

    public float getConsumption() {
        return currentReading - previousReading;
    }

    public float getAmount() {
        return getConsumption() * meter.getMeterType().getRate();
    }

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }
}
