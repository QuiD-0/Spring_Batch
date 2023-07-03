package com.quid.batch.job.usecase

import com.quid.batch.common.excel.ExcelDownloader
import com.quid.batch.coupon.domain.Coupon
import com.quid.batch.coupon.model.CouponExcelDto
import com.quid.batch.coupon.repository.CouponRepository
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus.FINISHED
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

interface CouponListExcel{

    fun download(): Step

    @Service
    class CouponListExcelImpl(
            private val stepBuilderFactory: StepBuilderFactory,
            private val couponRepository: CouponRepository,
            private val excelDownloader: ExcelDownloader
    ) : CouponListExcel {

        override fun download(): Step =
            stepBuilderFactory.get("couponListExcel")
                .tasklet { _, _ ->
                    couponRepository.findUsableCoupon()
                            .also { excelDown(it) }
                    FINISHED
                }
                .build()

        private fun excelDown(it: List<Coupon>) {
            val home = System.getProperty("user.home")
            val today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")).toString()
            excelDownloader.download("$home\\Downloads\\available_coupon_$today.xlsx","쿠폰리스트", it.map { CouponExcelDto.of(it) })
        }

    }
}
