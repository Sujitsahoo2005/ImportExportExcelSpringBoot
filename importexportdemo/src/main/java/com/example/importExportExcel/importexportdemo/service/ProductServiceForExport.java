package com.example.importExportExcel.importexportdemo.service;

import com.example.importExportExcel.importexportdemo.entity.Product;
import com.example.importExportExcel.importexportdemo.helper.HelperForExport;
import com.example.importExportExcel.importexportdemo.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceForExport {

    @Autowired
    private ProductRepo productRepo;

    public ByteArrayInputStream getActualData() throws IOException {
        List<Product> products = productRepo.findAll();

        ByteArrayInputStream byteArrayInputStream = HelperForExport.dataToExcel(products);
        return byteArrayInputStream;
    }

}
