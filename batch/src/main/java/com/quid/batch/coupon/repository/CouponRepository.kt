package com.quid.batch.coupon.repository

import com.quid.batch.coupon.domain.Coupon
import com.quid.batch.coupon.domain.createCoupon
import com.quid.batch.coupon.repository.jpa.CouponJpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

interface CouponRepository {
    fun findUsableCoupon(): List<Coupon>
    fun saveAll(couponList: List<Coupon>): List<Coupon>

    @Repository
    class CouponRepositoryImpl(
        private val couponJpaRepository: CouponJpaRepository
    ) : CouponRepository {
        override fun findUsableCoupon(): List<Coupon> {
            return couponJpaRepository.findByDeletedAndIsUsedAndExpiredAtAfter(
                isUsed = false,
                deleted = false,
                expiredAt = LocalDateTime.now()
            )
                .map { it.toDomain() }
        }

        override fun saveAll(couponList: List<Coupon>): List<Coupon> =
            couponList.map { toEntity{ it } }
                .let { couponJpaRepository.saveAll(it) }
                .map { it.toDomain() }
    }
}