package com.billing_ktaller.water_billing.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Entity
public class MeterStatuses {
    /**
     * This class is used to store the status of the meter.
     * For example, the status of the meter can be active, inactive, or suspended.
     */
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Setter
    @Getter
    @Column(unique = true)
    private String status;
    @Setter
    @Getter
    private String description;
    @CreatedDate
    @Getter
    private Instant createdAt;
    @LastModifiedDate
    @Getter
    private Instant updatedAt;

    public MeterStatuses() {
    }

    public MeterStatuses(String status, String description) {
        this.status = status;
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
