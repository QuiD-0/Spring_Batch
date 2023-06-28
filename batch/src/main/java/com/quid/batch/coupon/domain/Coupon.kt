package com.quid.batch.coupon.domain

import java.time.LocalDateTime

class Coupon(
        private val id: Long? = null,
        private val userId: Long,
        private val name: String,
        private val discount: Int,
        private val isUsed: Boolean = false,
        private val createdAt: LocalDateTime = LocalDateTime.now(),
        private val expiredAt: LocalDateTime,
        private val couponType: CouponType,
){
    override fun toString(): String {
        return "Coupon(userId=$userId, name='$name', discount=$discount, isUsed=$isUsed, createdAt=$createdAt, expiredAt=$expiredAt, couponType=$couponType)"
    }
}

fun createCoupon(
        userId: Long,
        name: String,
        discount: Int,
        expiredAt: LocalDateTime,
        couponType: CouponType,
) = Coupon(
        userId = userId,
        name = name,
        discount = discount,
        expiredAt = expiredAt,
        couponType = couponType,
)