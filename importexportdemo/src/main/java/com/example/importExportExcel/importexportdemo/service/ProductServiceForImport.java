package com.example.importExportExcel.importexportdemo.service;

import com.example.importExportExcel.importexportdemo.entity.Product;
import com.example.importExportExcel.importexportdemo.helper.HelperForImport;
import com.example.importExportExcel.importexportdemo.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceForImport {

    @Autowired
    private ProductRepo productRepo;
    public void save(MultipartFile file){
        try {
            List<Product> products = HelperForImport.convertExcelToListOfProduct(file.getInputStream());
            this.productRepo.saveAll(products);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Product> getAllProducts(){
        return this.productRepo.findAll();
    }

}
