package com.csmis.service;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class ExcelService {

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
    public static MultipartFile convertExcelToCsv(MultipartFile excelFile) throws IOException {
        FileInputStream inputStream = new FileInputStream(convert(excelFile));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        File csvFile = new File("temp.csv");
        FileWriter csvWriter = new FileWriter(csvFile);
        for (Row row : sheet) {
            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case STRING:
                        csvWriter.append(cell.getStringCellValue().replaceAll(",", " ") + ",");
                        break;
                    case NUMERIC:
                        csvWriter.append(cell.getNumericCellValue() + ",");
                        break;
                    case BOOLEAN:
                        csvWriter.append(cell.getBooleanCellValue() + ",");
                        break;
                    default:
                        csvWriter.append(",");
                }
            }
            csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
        workbook.close();
        inputStream.close();
        FileInputStream csvStream = new FileInputStream(csvFile);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len;
        while ((len = csvStream.read(buf)) > 0) {
            bos.write(buf, 0, len);
        }
        byte[] csvBytes = bos.toByteArray();

        csvStream.close();
        csvFile.delete();
        return new ByteArrayMultipartFile(excelFile.getName(), excelFile.getOriginalFilename(), MediaType.TEXT_PLAIN_VALUE, csvBytes);
    }

    private static File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private static class ByteArrayMultipartFile implements MultipartFile {

        private final String name;
        private final String originalFilename;
        private final String contentType;
        private final byte[] bytes;

        public ByteArrayMultipartFile(String name, String originalFilename, String contentType, byte[] bytes) {
            this.name = name;
            this.originalFilename = originalFilename;
            this.contentType = contentType;
            this.bytes = bytes;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getOriginalFilename() {
            return originalFilename;
        }

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public boolean isEmpty() {
            return bytes.length == 0;
        }

        @Override
        public long getSize() {
            return bytes.length;
        }

        @Override
        public byte[] getBytes() throws IOException {
            return bytes;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(bytes);
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {
            new FileOutputStream(dest).write(bytes);
        }
    }
}