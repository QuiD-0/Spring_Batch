package com.quid.batch.coupon.repository.jpa

import com.quid.batch.coupon.repository.CouponEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CouponJpaRepository : JpaRepository<CouponEntity, Long>