package com.quid.batch.pass;

import com.quid.batch.pass.entity.BulkPass;
import com.quid.batch.pass.entity.BulkPassStatus;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BulkPassRepository extends JpaRepository<BulkPass, Integer> {

    List<BulkPass> findByBulkPassStatusAndStartedAtGreaterThan(BulkPassStatus status, LocalDateTime startedAt);

}
