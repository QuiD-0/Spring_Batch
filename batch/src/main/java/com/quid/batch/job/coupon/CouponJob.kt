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
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

interface CouponJob {

    fun publishAll(): Step

    @Service
    class CouponJobImpl(
            private val stepBuilderFactory: StepBuilderFactory,
            private val entityManagerFactory: EntityManagerFactory
    ) : CouponJob {

        @JobScope
        override fun publishAll(): Step {
            return stepBuilderFactory.get("publishAll")
                    .chunk<Long, Coupon>(CHUNK_SIZE)
                    .reader(findUser())
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

        private fun findUser(): JpaPagingItemReader<Long> {
            return JpaPagingItemReader<Long>().apply {
                setQueryString("select u.id from UserEntity u")
                setEntityManagerFactory(entityManagerFactory)
                pageSize = CHUNK_SIZE
            }
        }

        companion object {
            private const val CHUNK_SIZE = 5
        }

    }
}