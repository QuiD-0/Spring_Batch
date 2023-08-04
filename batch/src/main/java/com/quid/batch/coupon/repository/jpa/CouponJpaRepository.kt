package com.quid.batch.coupon.repository.jpa

import com.quid.batch.coupon.repository.CouponEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface CouponJpaRepository : JpaRepository<CouponEntity, Long> {

    fun findByDeletedAndIsUsedAndExpiredAtAfter(deleted: Boolean, isUsed: Boolean, expiredAt: LocalDateTime): List<CouponEntity>
}