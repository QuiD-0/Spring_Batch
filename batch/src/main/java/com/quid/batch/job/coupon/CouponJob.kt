package com.quid.batch.job.coupon

import com.quid.batch.coupon.domain.Coupon
import com.quid.batch.coupon.domain.CouponType
import com.quid.batch.coupon.domain.createCoupon
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Service
import java.time.LocalDateTime

interface CouponJob {

    fun publishAll(): Step

    @Service
    class CouponJobImpl(
            private val stepBuilderFactory: StepBuilderFactory
    ) : CouponJob {

        @JobScope
        override fun publishAll(): Step {
            return stepBuilderFactory.get("publishAll")
                    .chunk<Long, Coupon>(1)
                    .reader(findAll())
                    .processor(makeCoupon())
                    .writer(saveAll())
                    .build()
        }

        private fun saveAll(): ItemWriter<in Coupon> {
            return ItemWriter {
                println(it)
            }
        }

        private fun makeCoupon(): ItemProcessor<in Long, out Coupon> {
            return ItemProcessor {
                createCoupon(
                    userId = it,
                    name = "쿠폰 $it",
                    discount = 10,
                    expiredAt = LocalDateTime.now().plusDays(7),
                    couponType = CouponType.PERCENTAGE
                )
            }
        }

        private fun findAll(): ItemReader<Long> {
            return CustomItemReader(arrayListOf(1L, 2L, 3L, 4L, 5L))
        }


    }
}