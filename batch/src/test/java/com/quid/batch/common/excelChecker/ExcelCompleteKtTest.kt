package com.quid.batch.common.excelChecker

import com.quid.batch.common.excel.excelPublishCheck
import com.quid.batch.common.excel.excelPublishComplete
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled

@Disabled
class ExcelCompleteKtTest{

    @Test
    fun excelPubCheck(){
        val fileName = "upload/test.xlsx"
        val result = excelPublishCheck(fileName)
        assertEquals(true, result)
    }

    @Test
    fun excelPubComplete(){
        val fileName = "upload/test.xlsx"
        excelPublishComplete(fileName)
    }
}