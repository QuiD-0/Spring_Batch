package com.quid.batch.common.excel

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileInputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class ExcelComplete(
    private val excelFileName: String,
) {

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

fun excelPublishComplete(fileName: String) {
    ExcelComplete(fileName).complete()
}



