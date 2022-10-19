package com.quid.batch.pass.repository;

import com.quid.batch.pass.entity.Pass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassRepository extends JpaRepository<Pass,Integer> {

}
