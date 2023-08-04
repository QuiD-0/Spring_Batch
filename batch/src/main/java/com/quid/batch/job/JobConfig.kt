package com.quid.batch.job

import com.quid.batch.coupon.usecase.AddFromExcelCoupon
import com.quid.batch.coupon.usecase.CouponListExcel
import com.quid.batch.coupon.usecase.DeleteExpiredCoupon
import com.quid.batch.coupon.usecase.PublishCoupon
import org.springframework.batch.core.Job
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JobConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val publishCoupon: PublishCoupon,
    private val deleteExpiredCoupon: DeleteExpiredCoupon,
    private val couponListExcel: CouponListExcel,
    private val addFromExcelCoupon: AddFromExcelCoupon
) {
    @Bean("couponJob")
    fun couponJob(): Job = jobBuilderFactory.get("couponJob")
        .start(publishCoupon.execute())
        .next(deleteExpiredCoupon.execute())
        .start(addFromExcelCoupon.execute())
        .build()

    @Bean("downloadJob")
    fun downloadJob(): Job = jobBuilderFactory.get("downloadJob")
        .start(couponListExcel.download())
        .build()
}