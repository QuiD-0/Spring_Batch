package com.quid.batch.coupon.repository

import com.quid.batch.coupon.domain.Coupon
import com.quid.batch.coupon.domain.CouponType
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "coupon")
class CouponEntity(
        @Id @GeneratedValue(strategy = IDENTITY) private val id: Long? = null,
        private val userId: Long,
        private val name: String,
        private val discount: Int,
        private val isUsed: Boolean = false,
        private val createdAt: LocalDateTime = LocalDateTime.now(),
        private val expiredAt: LocalDateTime,
        private val couponType: CouponType,
)

fun toEntity(coupon: () -> Coupon): CouponEntity = CouponEntity(
        userId = coupon().userId,
        name = coupon().name,
        discount = coupon().discount,
        isUsed = coupon().isUsed,
        createdAt = coupon().createdAt,
        expiredAt = coupon().expiredAt,
        couponType = coupon().couponType,
)