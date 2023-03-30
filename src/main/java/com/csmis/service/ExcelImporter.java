package com.csmis.service;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
@Service
public class ExcelImporter {

    public List<List<String>> readExcel(File file) throws IOException {
        List<List<String>> data = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            List<String> rowData = new ArrayList<>();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                rowData.add(cell.getStringCellValue());
            }
            data.add(rowData);
        }

        return data;
    }

}
