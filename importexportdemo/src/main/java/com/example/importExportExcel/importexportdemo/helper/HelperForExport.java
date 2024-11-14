package com.example.importExportExcel.importexportdemo.helper;

import com.example.importExportExcel.importexportdemo.entity.Product;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class HelperForExport {
    public static String[] HEADERS = {
            "productId",
            "productName",
            "productDesc",
            "productPrice"
    };

    public static String SHEET_NAME = "product_data";

    public static ByteArrayInputStream dataToExcel(List<Product> list) throws IOException {
        // create workbook
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            // create sheet
            Sheet sheet = workbook.createSheet(SHEET_NAME);

            // create row: header row
            Row row = sheet.createRow(0);

            for (int i = 0; i < HEADERS.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(HEADERS[i]);

            }

            // value rows
            int rowIndex = 1;
            Iterator<Product> iterator = list.iterator();
            for (Product p : list) {
                Row dataRow = sheet.createRow(rowIndex);
                rowIndex++;
                dataRow.createCell(0).setCellValue(p.getProductId());
                dataRow.createCell(1).setCellValue(p.getProductName());
                dataRow.createCell(2).setCellValue(p.getProductDesc());
                dataRow.createCell(3).setCellValue(p.getProductPrice());
            }

            workbook.write(out);

            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException();
        } finally {
            workbook.close();
            out.close();
        }

    }
}
