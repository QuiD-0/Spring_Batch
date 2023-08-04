package com.quid.batch.common.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import lombok.SneakyThrows;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;

public class Binder<Data> {

    private final static Integer FLUSH_SIZE = 100000;
    private final OutputStream outputStream;
    private final String sheetName;
    private final DataValues<Data> data;

    public Binder(OutputStream outputStream, String sheetName, List<Data> data,
        Internationalization lang) {
        this.outputStream = outputStream;
        this.sheetName = sheetName;
        this.data = new DataValues<>(data, lang);
    }

    @SneakyThrows
    public Binder(String path, String sheetName, List<Data> data) {
        this.outputStream = new FileOutputStream(path);
        this.sheetName = sheetName;
        this.data = new DataValues<>(data, Internationalization.KO);
    }

    public OutputStream build() {
        xlsxGenerator();
        return outputStream;
    }

    private void xlsxGenerator() {
        Workbook workbook = new Workbook(outputStream, sheetName, "1.0");
        Worksheet worksheet = workbook.newWorksheet(sheetName);
        headerDatas(worksheet, data.headerValue);
        try {
            fieldDatas(worksheet, data.fieldValue);
            workbook.finish();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void headerDatas(Worksheet sheet, DataValues.HeaderValue<Data> headerValue) {
        defaultHeader(sheet, headerValue.collect.size());

        for (int i = 0; i < headerValue.collect.size(); i++) {
            sheet.value(0, i, headerValue.get(i));
            sheet.width(i, headerValue.get(i).length() * 5);
        }
        sheet.setAutoFilter(0, 0, 0, headerValue.collect.size() - 1);

    }

    @SneakyThrows
    private void fieldDatas(Worksheet sheet, List<DataValues.FieldValue<Data>> fieldValues)
        throws IOException {
        for (int row = 0; row < fieldValues.size(); row++) {
            for (int col = 0; col < fieldValues.get(row).size(); col++) {
                sheet.value(row + 1, col, fieldValues.get(row).get(col));
            }
            if (row % FLUSH_SIZE == 0) {
                sheet.flush();
            }
        }
        sheet.flush();
        sheet.finish();
    }

    private void defaultHeader(Worksheet sheet, Integer headerSize) {
        sheet.range(0, 0, 0, headerSize - 1)
            .style()
            .fillColor("808080")
            .verticalAlignment("center")
            .horizontalAlignment("center")
            .fontColor("FFFFFFFF")
            .bold()
            .set();
    }
}
