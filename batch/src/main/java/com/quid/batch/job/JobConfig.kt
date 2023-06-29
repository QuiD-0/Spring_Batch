package com.quid.batch.job

import com.quid.batch.job.coupon.CouponJob
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.boot.autoconfigure.batch.BatchProperties.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JobConfig(
        private val jobBuilderFactory: JobBuilderFactory,
        private val couponJob: CouponJob
) {
    @Bean("couponJob")
    fun couponJob() = jobBuilderFactory.get("couponJob")
            .start(couponJob.publishAll())
            .build()
}