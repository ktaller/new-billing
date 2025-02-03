package com.billing_ktaller.water_billing.repositories;

import com.billing_ktaller.water_billing.models.Customers;
import com.billing_ktaller.water_billing.models.MeterStatuses;
import com.billing_ktaller.water_billing.models.MeterTypes;
import com.billing_ktaller.water_billing.models.Meters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeterRepository extends JpaRepository<Meters, Long> {
    Optional<Meters> findByMeterNumber(String meterNumber);

    List<Meters> findByCustomer(Customers customer);

    List<Meters> findByStatus(MeterStatuses status);

    List<Meters> findByMeterType(MeterTypes meterType);

    List<Meters> findByIsActive(boolean isActive);
}
