package com.quid.batch.coupon.usecase

import com.quid.batch.common.ExcelComponent.excelPublishCheck
import com.quid.batch.common.ExcelComponent.excelPublishComplete
import com.quid.batch.coupon.domain.Coupon
import com.quid.batch.coupon.repository.CouponRepository
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Service
import java.io.File

interface AddFromExcelCoupon {
    fun execute(): Step

    @Service
    class AddFromExcelCouponImpl(
        private val stepBuilderFactory: StepBuilderFactory,
        private val couponRepository: CouponRepository,
    ) : AddFromExcelCoupon {

        override fun execute(): Step =
            stepBuilderFactory.get("addFromExcelCoupon")
                .tasklet { _, _ ->
                    takeIf { excelPublishCheck(EXCEL_FILE_NAME) }
                         ?:let{
                             readCouponListFromExcel()
                                 .also{ couponRepository.saveAll(it) }
                                 .also { excelPublishComplete(EXCEL_FILE_NAME) }
                         }
                    RepeatStatus.FINISHED
                }
                .build()


        private fun readCouponListFromExcel(): List<Coupon> {
            val couponList = mutableListOf<Coupon>()
            val excelFile = File(EXCEL_FILE_NAME)
            println(excelFile.absolutePath)
            return couponList
        }

        companion object {
            private const val EXCEL_FILE_NAME = "upload/addCouponList.xlsx"
        }

    }
}