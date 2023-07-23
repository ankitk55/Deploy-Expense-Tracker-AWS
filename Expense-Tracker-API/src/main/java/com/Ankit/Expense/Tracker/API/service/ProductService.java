package com.Ankit.Expense.Tracker.API.service;

import com.Ankit.Expense.Tracker.API.model.Product;
import com.Ankit.Expense.Tracker.API.model.User;
import com.Ankit.Expense.Tracker.API.repository.IProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    IProductRepo productRepo;

    public String addProduct(User user, Product product) {
        product.setProductCreationDateStamp(LocalDate.now(ZoneId.of("Asia/Kolkata")));
        product.setUser(user);
        productRepo.save(product);
        return "product added";
    }

    public List<Product> getProductByDate(User user,LocalDate date) {
        return productRepo.findByUserAndProductCreationDateStamp(user,date);
    }

    public List<Product> findProductByUser(User user) {
        return productRepo.findByUser(user);
    }

    public void deleteProducts(List<Product> products) {
         productRepo.deleteAll(products);
    }

    public Product findById(Long id) {
       return productRepo.findById(id).orElse(null);
    }
}
