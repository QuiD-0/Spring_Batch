package com.quid.batch.job

import com.quid.batch.job.coupon.DeleteExpiredCoupon
import com.quid.batch.job.coupon.PublishCoupon
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.boot.autoconfigure.batch.BatchProperties.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JobConfig(
        private val jobBuilderFactory: JobBuilderFactory,
        private val publishCoupon: PublishCoupon,
        private val deleteExpiredCoupon: DeleteExpiredCoupon
) {
    @Bean("couponJob")
    fun couponJob() = jobBuilderFactory.get("couponJob")
            .start(publishCoupon.execute())
            .next(deleteExpiredCoupon.execute())
            .build()
}