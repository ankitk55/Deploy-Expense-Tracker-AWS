package com.Ankit.Expense.Tracker.API.repository;

import com.Ankit.Expense.Tracker.API.model.Product;
import com.Ankit.Expense.Tracker.API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IProductRepo extends JpaRepository<Product,Long> {
    List<Product> findByUserAndProductCreationDateStamp(User user, LocalDate date);

    List<Product> findByUser(User user);
}
