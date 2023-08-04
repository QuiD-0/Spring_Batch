package com.quid.batch.reader

import com.quid.batch.coupon.domain.Coupon
import com.quid.batch.coupon.domain.CouponType
import com.quid.batch.coupon.domain.createCoupon
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
@Qualifier("couponExcelReader")
class CouponExcelReader: ExcelReader {
    override fun read(excelFileName: String): List<Coupon> {
        val file = File(excelFileName)
        val workbook = XSSFWorkbook(file.inputStream())
        val sheet = workbook.getSheetAt(0)
        return IntRange(3,sheet.physicalNumberOfRows-1).map {
            getCouponData(sheet.getRow(it))
        }
    }

    private fun getCouponData(row: XSSFRow): Coupon {
        val date = row.getCell(3).localDateTimeCellValue.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        return createCoupon(
            row.getCell(0).numericCellValue.toLong(),
            row.getCell(1).stringCellValue,
            row.getCell(2).numericCellValue.toInt(),
            LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            CouponType.find(row.getCell(4).stringCellValue)
        )
    }
}