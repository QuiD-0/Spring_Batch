package com.quid.batch.reader

import com.quid.batch.coupon.domain.Coupon

@FunctionalInterface
interface ExcelReader {
    fun read(excelFileName: String): List<Coupon>

}

