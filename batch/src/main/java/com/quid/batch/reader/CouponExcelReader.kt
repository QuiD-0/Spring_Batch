package com.quid.batch.reader

import com.quid.batch.coupon.domain.Coupon
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
@Qualifier("couponExcelReader")
class CouponExcelReader: ExcelReader {
    override fun read(excelFileName: String): List<Coupon> {
        return listOf()
    }
}