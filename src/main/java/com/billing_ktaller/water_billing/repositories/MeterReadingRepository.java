package com.billing_ktaller.water_billing.repositories;

import com.billing_ktaller.water_billing.dto.MonthlyAccumulatedAmountReportDto;
import com.billing_ktaller.water_billing.dto.MonthlyConsumptionReportDto;
import com.billing_ktaller.water_billing.models.MeterReadings;
import com.billing_ktaller.water_billing.models.Meters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeterReadingRepository extends JpaRepository<MeterReadings, Long> {
    List<MeterReadings> findByMeter(Meters meter);

    List<MeterReadings> findByMeterOrderByCreatedAtDesc(Meters meter);

    Optional<MeterReadings> findTopByMeterOrderByCreatedAtDesc(Meters meter);

    @Query("SELECT new com.billing_ktaller.water_billing.dto.MonthlyConsumptionReportDto("
            + "EXTRACT(MONTH FROM m.createdAt) AS month, COUNT(*),"
            + "SUM(m.currentReading - m.previousReading))"
            + "FROM MeterReadings m GROUP BY month ORDER BY month ASC")
    List<MonthlyConsumptionReportDto> findConsumptionReport();

    @Query("SELECT new com.billing_ktaller.water_billing.dto.MonthlyAccumulatedAmountReportDto(" +
        "EXTRACT(MONTH FROM mr.createdAt), " +
        "SUM((mr.currentReading - mr.previousReading) * mtType.rate)) " + 
        "FROM MeterReadings mr " +
        "JOIN mr.meter mt " +
        "JOIN mt.meterType mtType " +
        "WHERE EXTRACT(YEAR FROM mr.createdAt) = :year " +
        "GROUP BY EXTRACT(MONTH FROM mr.createdAt), mtType.rate " +
        "ORDER BY EXTRACT(MONTH FROM mr.createdAt) ASC")
List<MonthlyAccumulatedAmountReportDto> findAccumulatedAmountReportForYear(@Param("year") int year);

}
