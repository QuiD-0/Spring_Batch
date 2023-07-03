package com.quid.batch.common.excel

import com.quid.batch.coupon.model.CouponExcelDto
import org.junit.jupiter.api.Test

class BinderTest {

    @Test
    fun excelTest(){
        val data = listOf(
                CouponExcelDto(1L, "test",  10, "2021-08-01", "2021-08-31", "PERCENTAGE"),
                CouponExcelDto(2L, "user",  10, "2021-08-01", "2021-08-31", "PERCENTAGE"),
        )
        Binder("C:\\Users\\quid\\Desktop\\excel.xlsx","쿠폰리스트", data).build()
    }
}