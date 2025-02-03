package com.billing_ktaller.water_billing.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Entity
public class Meters {
    /**
     * This class is used to store the meter details of the customer.
     */
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Setter
    @Getter
    @Column(unique = true)
    private String meterNumber;
    @Setter
    @Getter
    @ManyToOne
    private Customers customer;
    @Setter
    @Getter
    private double locationLat;
    @Setter
    @Getter
    private double locationLng;
    @Setter
    @Getter
    @ManyToOne
    private MeterStatuses status;
    @Setter
    @Getter
    @ManyToOne
    private MeterTypes meterType;
    @Getter(lombok.AccessLevel.PRIVATE)
    @Setter
    private boolean isActive;
    @CreatedDate
    @Getter
    private Instant createdAt;
    @LastModifiedDate
    @Getter
    private Instant updatedAt;

    public Meters() {
    }

    public Meters(String meterNumber, Customers customer, double locationLat, double locationLng, MeterStatuses status,
                  MeterTypes meterType) {
        this.meterNumber = meterNumber;
        this.customer = customer;
        this.locationLat = locationLat;
        this.locationLng = locationLng;
        this.status = status;
        this.meterType = meterType;
        this.isActive = true;
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
