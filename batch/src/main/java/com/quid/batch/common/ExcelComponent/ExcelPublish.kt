package com.quid.batch.common.ExcelComponent

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileInputStream


class ExcelPublish(
    private val excelFileName: String,
) {
    fun check(): Boolean {
        val file = FileInputStream(File(excelFileName).absolutePath)
        val workbook = XSSFWorkbook(file)
        val sheet = workbook.getSheetAt(0)
        val row = sheet.getRow(1)
        val cell = row.getCell(0)
        return cell.stringCellValue != "N"
    }

    fun complete() {
        TODO()
    }

}

fun excelPublishComplete(fileName: String): Unit {
    ExcelPublish(fileName).complete()
}

fun excelPublishCheck(fileName: String): Boolean =
    ExcelPublish(fileName).check()


