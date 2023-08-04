package com.quid.batch.common.excel

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileInputStream

class PublishChecker(
    private val excelFileName: String,
) {
    fun check(): Boolean {
        val file = FileInputStream(File(excelFileName).absolutePath)
        val workbook = XSSFWorkbook(file)
        val sheet = workbook.getSheetAt(0)
        val row = sheet.getRow(1)
        val cell = row.getCell(0)
        return cell.stringCellValue == "Y"
    }
}

fun excelPublishCheck(fileName: String): Boolean =
    PublishChecker(fileName).check()
