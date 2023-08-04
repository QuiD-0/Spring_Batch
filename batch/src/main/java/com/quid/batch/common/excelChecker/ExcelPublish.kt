package com.quid.batch.common.excelChecker

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileInputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class ExcelPublish(
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

    fun complete() {
        val file = FileInputStream(File(excelFileName).absolutePath)
        val workbook = XSSFWorkbook(file)
        val sheet = workbook.getSheetAt(0)
        val row = sheet.getRow(1)
        val isPublished = row.getCell(0)
        isPublished.setCellValue("Y")

        val date = row.getCell(1)
        date.setCellValue(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString())

        file.close();

    }

}

fun excelPublishComplete(fileName: String): Unit {
    ExcelPublish(fileName).complete()
}

fun excelPublishCheck(fileName: String): Boolean =
    ExcelPublish(fileName).check()


