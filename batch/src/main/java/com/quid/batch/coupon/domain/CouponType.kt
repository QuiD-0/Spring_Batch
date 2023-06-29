package com.quid.batch.coupon.domain

import java.util.EnumSet

enum class CouponType {
    PERCENTAGE,
    AMOUNT;

    companion object {
        fun find(name: String): CouponType =
            EnumSet.allOf(CouponType::class.java)
                .stream().filter { it.name == name }
                .findFirst().orElseThrow { IllegalArgumentException("CouponType not found for name: $name") }
    }
}