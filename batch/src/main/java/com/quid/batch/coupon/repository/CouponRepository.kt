package com.quid.batch.coupon.repository

import com.quid.batch.coupon.domain.Coupon
import org.springframework.stereotype.Repository

interface CouponRepository {
    fun findUsableCoupon():List<Coupon>

    @Repository
    class CouponRepositoryImpl : CouponRepository {
        override fun findUsableCoupon(): List<Coupon> {
            return emptyList()
        }
    }
}