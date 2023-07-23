package com.Ankit.Expense.Tracker.API.repository;

import com.Ankit.Expense.Tracker.API.model.Expense;
import com.Ankit.Expense.Tracker.API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IExpenseRepo extends JpaRepository<Expense,Long> {



    List<Expense> findByUser(User user);
}
