package com.quid.batch.coupon.usecase

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
                    takeIf { checkExcelFile() }
                         ?.let{
                             readCouponListFromExcel()
                                 .also{ couponRepository.saveAll(it) }
                                 .also { completeExcelFile() }
                         }
                        ?: throw IllegalArgumentException("Excel file already published")
                    RepeatStatus.FINISHED
                }
                .build()

        private fun completeExcelFile() {
            val excelFile = File(EXCEL_FILE_NAME)
            excelFile.renameTo(File("${excelFile.absolutePath}.complete"))
        }

        private fun checkExcelFile(): Boolean {
            val excelFile = File(EXCEL_FILE_NAME)
            return excelFile.exists()
        }

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