package com.quid.batch.packaze.repository;

import com.quid.batch.packaze.entity.Package;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<Package, Integer> {
    List<Package> findByCreatedAtAfter(LocalDateTime datetime, Pageable pageable);
}
