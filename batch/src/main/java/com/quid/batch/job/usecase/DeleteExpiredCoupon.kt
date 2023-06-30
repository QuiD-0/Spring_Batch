package com.quid.batch.job.usecase

import com.quid.batch.coupon.repository.CouponEntity
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.persistence.EntityManagerFactory

interface DeleteExpiredCoupon {

    fun execute(): Step

    @Service
    class DeleteExpiredCouponImpl(
            private val stepBuilderFactory: StepBuilderFactory,
            private val entityManagerFactory: EntityManagerFactory
    ) : DeleteExpiredCoupon {

        override fun execute(): Step = stepBuilderFactory.get("deleteExpiredCoupon")
                .chunk<CouponEntity, CouponEntity>(CHUNK_SIZE)
                .reader(findExpiredCoupon())
                .processor(updateDeleted())
                .writer(persist())
                .build()


        private fun findExpiredCoupon(): JpaPagingItemReader<out CouponEntity> =
                JpaPagingItemReader<CouponEntity>().apply {
                    setQueryString("select c from CouponEntity c where c.expiredAt < :now and c.deleted = false")
                    setEntityManagerFactory(entityManagerFactory)
                    setParameterValues(mapOf("now" to LocalDateTime.now()))
                    pageSize = CHUNK_SIZE
                }.also {
                    println(it)
                }

        private fun updateDeleted(): ItemProcessor<in CouponEntity, out CouponEntity> =
                ItemProcessor {
                    it.copy(deleted = true)
                }


        private fun persist(): JpaItemWriter<in CouponEntity> =
                JpaItemWriter<CouponEntity>().apply {
                    setEntityManagerFactory(entityManagerFactory)
                }

        companion object {
            private const val CHUNK_SIZE = 10
        }
    }
}