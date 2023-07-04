package com.quid.batch.coupon.domain

import java.time.LocalDateTime

class Coupon(
    val id: Long? = null,
    val userId: Long,
    val name: String,
    val discount: Int,
    val isUsed: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val expiredAt: LocalDateTime,
    val couponType: CouponType,
) {
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