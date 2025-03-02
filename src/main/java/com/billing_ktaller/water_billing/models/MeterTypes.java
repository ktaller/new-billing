package com.billing_ktaller.water_billing.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Getter
@Entity
public class MeterTypes {
    /**
     * This class is used to store the type of the meter.
     * For example, the type of the meter can be residential, commercial, or
     * industrial.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Setter
    @Column(unique = true)
    private String type;
    @Setter
    private String description;
    @Setter
    private float rate;
    // add standing charge
    /*
    @Setter
    @Getter
    private float standingCharge;
    */
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    public MeterTypes() {
    }

    public MeterTypes(String type, String description) {
        this.type = type;
        this.description = description;
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
