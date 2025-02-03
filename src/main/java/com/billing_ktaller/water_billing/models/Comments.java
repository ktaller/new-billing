package com.billing_ktaller.water_billing.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Entity
public class Comments {
    /**
     * This class is used to store comments related to customer's meter and
     * readings.
     */
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Setter
    @Getter
    private String comment;
    // one meter can have multiple comments
    @Setter
    @Getter
    @ManyToOne
    private Meters meter;
    @CreatedDate
    @Getter
    private Instant createdAt;
    @LastModifiedDate
    @Getter
    private Instant updatedAt;

    public Comments() {
    }

    public Comments(String comment, Meters meter) {
        this.comment = comment;
        this.meter = meter;
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
