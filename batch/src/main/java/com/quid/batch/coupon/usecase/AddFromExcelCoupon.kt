package com.quid.batch.coupon.usecase

import com.quid.batch.common.ExcelComponent.excelPublishCheck
import com.quid.batch.common.ExcelComponent.excelPublishComplete
import com.quid.batch.coupon.gateway.excel.CouponExcelReader
import com.quid.batch.coupon.gateway.repository.CouponRepository
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Service

interface AddFromExcelCoupon {
    fun execute(): Step

    @Service
    class AddFromExcelCouponImpl(
        private val stepBuilderFactory: StepBuilderFactory,
        private val couponRepository: CouponRepository,
        private val couponExcelReader : CouponExcelReader
    ) : AddFromExcelCoupon {

        override fun execute(): Step = stepBuilderFactory.get("addFromExcelCoupon")
            .tasklet { _, _ ->
                if(excelPublishCheck(EXCEL_FILE_NAME)){
                    couponExcelReader.read(EXCEL_FILE_NAME)
                        .also { couponRepository.saveAll(it) }
                        .also { excelPublishComplete(EXCEL_FILE_NAME) }
                }
                RepeatStatus.FINISHED
            }
            .build()

        companion object {
            private const val EXCEL_FILE_NAME = "upload/addCouponList.xlsx"
        }

    }
}