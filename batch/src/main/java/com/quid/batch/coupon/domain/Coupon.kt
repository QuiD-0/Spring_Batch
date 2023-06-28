package com.quid.batch.coupon.domain

class Coupon(
        val id: Long,
        val userId: Long,
        val name: String,
        val discount: Int,
        val isUsed: Boolean,
        val createdAt: String,
        val expiredAt: String,
        val couponType: CouponType,
) {
}