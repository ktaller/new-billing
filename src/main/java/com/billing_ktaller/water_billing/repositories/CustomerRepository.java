package com.billing_ktaller.water_billing.repositories;

import com.billing_ktaller.water_billing.models.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customers, Long> {
    Optional<Customers> findByPhoneNumber(String phoneNumber);

    Optional<Customers> findByEmail(String email);
}
