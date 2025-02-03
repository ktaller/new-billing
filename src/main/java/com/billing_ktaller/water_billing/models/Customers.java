package com.billing_ktaller.water_billing.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Getter
@Entity
public class Customers {
    // personal details of the customer such as name, phone number
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Setter
    @NotBlank(message = "First name is required")
    private String firstName;
    @Setter
    @NotBlank(message = "Last name is required")
    private String lastName;
    @Setter
    @Column(unique = true)
    @NotBlank(message = "Phone number is required")
    // phone number can be 07******** or 01********
    @Pattern(regexp = "(07|01)\\d{8}", message = "Invalid phone number")
    private String phoneNumber;
    @Setter
    @Column(unique = true)
    @Email(message = "Invalid email")
    private String email;
    @Setter
    @NotBlank(message = "Address is required")
    private String address;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    public Customers() {
    }

    public Customers(String firstName, String lastName, String phoneNumber, String email, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"firstName\":\"" + firstName + '\"' +
                ", \"lastName\":\"" + lastName + '\"' +
                ", \"phoneNumber\":\"" + phoneNumber + '\"' +
                ", \"email\":\"" + email + '\"' +
                ", \"address\":\"" + address + '\"' +
                '}';
    }
}
