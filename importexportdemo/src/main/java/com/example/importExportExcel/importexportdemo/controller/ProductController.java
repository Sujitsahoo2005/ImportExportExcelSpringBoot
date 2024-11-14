package com.example.importExportExcel.importexportdemo.controller;

import com.example.importExportExcel.importexportdemo.entity.Product;
import com.example.importExportExcel.importexportdemo.helper.HelperForImport;
import com.example.importExportExcel.importexportdemo.service.ProductServiceForExport;
import com.example.importExportExcel.importexportdemo.service.ProductServiceForImport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductServiceForImport productServiceForImport;

    @Autowired
    private ProductServiceForExport productServiceForExport;

    @PostMapping("/product/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file){
        if(HelperForImport.checkExcelFormat(file)){
            // true

            this.productServiceForImport.save(file);
            return ResponseEntity
                    .ok(Map.of("message", "File is uploaded and data is saved to db"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("please upload excel file");
    }

    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return this.productServiceForImport.getAllProducts();
    }

    @GetMapping("/download/excel/products")
    public ResponseEntity<Resource> getAllProductsInExcel() throws IOException {
        String filename = "products.xlsx";

        ByteArrayInputStream actualData = productServiceForExport.getActualData();
        InputStreamResource file = new InputStreamResource(actualData);

        ResponseEntity<Resource> body = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = " + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
        return body;
    }


}
