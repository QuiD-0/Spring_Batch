package com.quid.batch.job

import com.quid.batch.job.usecase.CouponListExcel
import com.quid.batch.job.usecase.DeleteExpiredCoupon
import com.quid.batch.job.usecase.PublishCoupon
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.boot.autoconfigure.batch.BatchProperties.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JobConfig(
        private val jobBuilderFactory: JobBuilderFactory,
        private val publishCoupon: PublishCoupon,
        private val deleteExpiredCoupon: DeleteExpiredCoupon,
        private val couponListExcel: CouponListExcel
) {
    @Bean("couponJob")
    fun couponJob() = jobBuilderFactory.get("couponJob")
            .start(publishCoupon.execute())
            .next(deleteExpiredCoupon.execute())
            .next(couponListExcel.download())
            .build()
}