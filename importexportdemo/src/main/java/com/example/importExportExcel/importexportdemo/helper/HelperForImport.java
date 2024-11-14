package com.example.importExportExcel.importexportdemo.helper;

import com.example.importExportExcel.importexportdemo.entity.Product;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HelperForImport {

    // check that file is of Excel type or not
    public static boolean checkExcelFormat(MultipartFile file) {
        String contentType = file.getContentType();
        assert contentType != null;
        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return true;
        } else {
            return false;
        }
    }

    // convert Excel to list o products
    public static List<Product> convertExcelToListOfProduct(InputStream is) {
        List<Product> list = new ArrayList<>();
        try{
            // getting the workbook
            XSSFWorkbook workbook = new XSSFWorkbook(is);

            // getting the sheet "data" from workbook
            XSSFSheet sheet = workbook.getSheet("data");

            int rowNumber = 0;

            // getting the rows from sheet
            Iterator<Row> iterator = sheet.iterator();

            // iterating the row and checking value is present or not
            while(iterator.hasNext()){

                Row row = iterator.next();
                if(rowNumber == 0){
                    rowNumber++;
                    continue;
                }

                // getting the cells from row
                Iterator<Cell> cells = row.iterator();
                int cid = 0;
                Product p = new Product();
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    switch (cid){
                        case 0:
                            p.setProductId((int)cell.getNumericCellValue());
                            break;
                        case 1:
                            p.setProductName(cell.getStringCellValue());
                            break;
                        case 2:
                            p.setProductDesc(cell.getStringCellValue());
                            break;
                        case 3:
                            p.setProductPrice(cell.getNumericCellValue());
                            break;
                        default:
                            break;
                    }
                    cid++;
                }
                list.add(p);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
