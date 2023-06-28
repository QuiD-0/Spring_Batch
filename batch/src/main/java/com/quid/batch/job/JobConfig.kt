package com.quid.batch.job

import com.quid.batch.job.coupon.CouponJob
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class JobConfig(
        private val jobBuilderFactory: JobBuilderFactory,
        private val couponJob: CouponJob
) {
    @Bean("couponJob")
    open fun couponJob() = jobBuilderFactory.get("couponJob")
            .start(couponJob.publishAll())
            .build()
}