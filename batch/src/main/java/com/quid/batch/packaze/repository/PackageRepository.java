package com.quid.batch.packaze.repository;

import com.quid.batch.packaze.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<Package, Integer> {

}
