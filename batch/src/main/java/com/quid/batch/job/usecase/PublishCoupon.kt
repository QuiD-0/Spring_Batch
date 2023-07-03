package com.quid.batch.job.usecase

import com.quid.batch.coupon.domain.CouponType
import com.quid.batch.coupon.domain.createCoupon
import com.quid.batch.coupon.repository.CouponEntity
import com.quid.batch.coupon.repository.toEntity
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.persistence.EntityManagerFactory

interface PublishCoupon {

    fun execute(): Step

    @Service
    class CouponJobImpl(
            private val stepBuilderFactory: StepBuilderFactory,
            private val entityManagerFactory: EntityManagerFactory
    ) : PublishCoupon {

        @JobScope
        override fun execute(): Step {
            return stepBuilderFactory.get("publishAll")
                    .chunk<Long, CouponEntity>(CHUNK_SIZE)
                    .reader(findUser())
                    .processor(makeCoupon())
                    .writer(persist())
                    .build()
        }

        private fun persist(): JpaItemWriter<in CouponEntity> {
            return JpaItemWriter<CouponEntity>().apply {
                setEntityManagerFactory(entityManagerFactory)
            }
        }

        private fun makeCoupon(): ItemProcessor<in Long, out CouponEntity> {
            return ItemProcessor {
                toEntity {
                    createCoupon(
                        userId = it,
                        name = "쿠폰 $it",
                        discount = 1000,
                        expiredAt = LocalDateTime.now().plusDays(7),
                        couponType = CouponType.AMOUNT
                    )
                }
            }
        }

        private fun findUser(): JpaPagingItemReader<Long> {
            return JpaPagingItemReader<Long>().apply {
                setQueryString("select u.id from UserEntity u where u.tier != 'BASIC'")
                setEntityManagerFactory(entityManagerFactory)
                pageSize = CHUNK_SIZE
            }
        }

        companion object {
            private const val CHUNK_SIZE = 10
        }

    }
}