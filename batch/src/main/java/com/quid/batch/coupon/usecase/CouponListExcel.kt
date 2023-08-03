package com.quid.batch.coupon.usecase

import com.quid.batch.common.excelDownloader.ExcelUtils
import com.quid.batch.coupon.domain.Coupon
import com.quid.batch.coupon.model.CouponExcelDto
import com.quid.batch.coupon.repository.CouponRepository
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus.FINISHED
import org.springframework.stereotype.Service

@FunctionalInterface
interface CouponListExcel {

    fun download(): Step

    @Service
    class CouponListExcelImpl(
        private val stepBuilderFactory: StepBuilderFactory,
        private val couponRepository: CouponRepository,
        private val excelUtils: ExcelUtils
    ) : CouponListExcel {

        override fun download(): Step =
            stepBuilderFactory.get("couponListExcel")
                .tasklet { _, _ ->
                    couponRepository.findUsableCoupon()
                        .also { excelDown(it) }
                    FINISHED
                }
                .build()

        private fun excelDown(it: List<Coupon>) =
            excelUtils.download("couponList", it.map { CouponExcelDto.of(it) })

    }
}
