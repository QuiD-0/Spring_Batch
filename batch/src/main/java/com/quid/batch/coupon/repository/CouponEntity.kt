package com.quid.batch.coupon.repository

import com.quid.batch.coupon.domain.Coupon
import com.quid.batch.coupon.domain.CouponType
import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "coupon")
class CouponEntity(
        @Id @GeneratedValue(strategy = IDENTITY) private val id: Long? = null,
        val userId: Long,
        val name: String,
        val discount: Int,
        val isUsed: Boolean = false,
        val createdAt: LocalDateTime = LocalDateTime.now(),
        val expiredAt: LocalDateTime,
        @Enumerated(EnumType.STRING)
        val couponType: CouponType,
        val deleted: Boolean = false,
) {
    fun copy(
            userId: Long = this.userId,
            name: String = this.name,
            discount: Int = this.discount,
            isUsed: Boolean = this.isUsed,
            createdAt: LocalDateTime = this.createdAt,
            expiredAt: LocalDateTime = this.expiredAt,
            couponType: CouponType = this.couponType,
            deleted: Boolean = this.deleted,
    ): CouponEntity {
        return CouponEntity(
                id = this.id,
                userId = userId,
                name = name,
                discount = discount,
                isUsed = isUsed,
                createdAt = createdAt,
                expiredAt = expiredAt,
                couponType = couponType,
                deleted = deleted,
        )
    }
}

fun toEntity(coupon: () -> Coupon): CouponEntity = CouponEntity(
        userId = coupon().userId,
        name = coupon().name,
        discount = coupon().discount,
        isUsed = coupon().isUsed,
        createdAt = coupon().createdAt,
        expiredAt = coupon().expiredAt,
        couponType = coupon().couponType,
)