package com.billing_ktaller.water_billing.repositories;

import com.billing_ktaller.water_billing.models.Comments;
import com.billing_ktaller.water_billing.models.Meters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByMeter(Meters meter);
}
