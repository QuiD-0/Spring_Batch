package com.quid.batch.coupon.usecase

import com.quid.batch.common.excelChecker.excelPublishCheck
import com.quid.batch.common.excelChecker.excelPublishComplete
import com.quid.batch.reader.ExcelReader
import com.quid.batch.coupon.repository.CouponRepository
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

interface AddFromExcelCoupon {
    fun execute(): Step

    @Service
    class AddFromExcelCouponImpl(
        private val stepBuilderFactory: StepBuilderFactory,
        private val couponRepository: CouponRepository,
        @Qualifier("couponExcelReader")
        private val couponExcelReader : ExcelReader
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