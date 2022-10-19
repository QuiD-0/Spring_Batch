package com.quid.batch.booking.repository;

import com.quid.batch.booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE Booking b" +
        "          SET b.usedPass = :usedPass," +
        "              b.modifiedAt = CURRENT_TIMESTAMP" +
        "        WHERE b.passSeq = :passSeq")
    int updateUsedPass(Integer passSeq, boolean usedPass);
}
