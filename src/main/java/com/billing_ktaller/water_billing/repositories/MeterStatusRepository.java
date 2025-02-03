package com.billing_ktaller.water_billing.repositories;

import com.billing_ktaller.water_billing.models.MeterStatuses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeterStatusRepository extends JpaRepository<MeterStatuses, Long> {

}
