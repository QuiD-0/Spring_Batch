package com.quid.batch.coupon.gateway.excel

import com.quid.batch.coupon.domain.Coupon
import org.springframework.stereotype.Component

@Component
class CouponExcelReader {
    fun read(excelFileName: String): List<Coupon> {
        return listOf()
    }
}