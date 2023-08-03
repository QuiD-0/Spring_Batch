package com.quid.batch.coupon.usecase

import com.quid.batch.coupon.repository.CouponRepository
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
    ) : AddFromExcelCoupon {

        override fun execute(): Step =
            stepBuilderFactory.get("addFromExcelCoupon")
                .tasklet { _, _ ->
                    readCouponListFromExcel()
                        .also{ couponRepository.saveAll(it) }
                    RepeatStatus.FINISHED
                }
                .build()

    }
}