package com.quid.batch.reader

import org.junit.Test

class CouponExcelReaderTest(){

    @Test
    fun readExcelTest(){
        val couponExcelReader = CouponExcelReader()
        val couponList = couponExcelReader.read("upload/test.xlsx")
        println(couponList)
    }
}