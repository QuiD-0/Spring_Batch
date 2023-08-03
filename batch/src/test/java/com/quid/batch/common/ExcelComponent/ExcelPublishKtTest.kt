package com.quid.batch.common.ExcelComponent

import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class ExcelPublishKtTest{

    @Test
    fun excelPubCheck(){
        val fileName = "upload/test.xlsx"
        val result = excelPublishCheck(fileName)
        assertEquals(false, result)
    }
}