package com.quid.batch.coupon.repository

import com.quid.batch.coupon.domain.Coupon
import com.quid.batch.coupon.repository.jpa.CouponJpaRepository
import org.springframework.stereotype.Repository

interface CouponRepository {
    fun findUsableCoupon():List<Coupon>

    @Repository
    class CouponRepositoryImpl(
            private val couponJpaRepository: CouponJpaRepository
    ) : CouponRepository {
        override fun findUsableCoupon(): List<Coupon> {
            return couponJpaRepository.findByIsUsedAndDeleted(isUsed = false, deleted = false)
                    .map { it.toDomain() }
        }
    }
}