package com.Ankit.Expense.Tracker.API.service;

import com.Ankit.Expense.Tracker.API.model.Expense;
import com.Ankit.Expense.Tracker.API.model.Product;
import com.Ankit.Expense.Tracker.API.model.User;
import com.Ankit.Expense.Tracker.API.repository.IExpenseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {
    @Autowired
    IExpenseRepo expenseRepo;
    @Autowired
    ProductService productService;

    public ResponseEntity<String> addExpense(User user, Expense expense) {
        expense.setExpenseDateStamp(LocalDate.now(ZoneId.of("Asia/Kolkata")));
        expense.setUser(user);
        List<Product>productList =expense.getProduct();
        List<Long>productIds =new ArrayList<>();
        for(Product product :productList){
           productIds.add(product.getProductId());
        }
        for(Long id:productIds){
          Product product=  productService.findById(id);
          if(product==null ||!product.getUser().equals(user)){
              return ResponseEntity.ok("invalid product");
          }

        }
        expenseRepo.save(expense);
        return ResponseEntity.ok("expense Added");
    }

    public List<Expense> weeklyExpense(User user) {
        List<Expense> allExpense = expenseRepo.findByUser(user);
        List<Expense> weeklyExpense = new ArrayList<>();

        LocalDate currentDate= LocalDate.now(ZoneId.of("Asia/Kolkata"));

        LocalDate lastWeekDate = currentDate.minusWeeks(1);
        for (Expense ex : allExpense) {
            if (ex.getExpenseDateStamp().isEqual(currentDate)||ex.getExpenseDateStamp().isBefore(currentDate) && ex.getExpenseDateStamp().isAfter(lastWeekDate)) {
                weeklyExpense.add(ex);

            }
        }
        return weeklyExpense;
    }

    public List<Expense> monthlyExpenses(User user) {
        List<Expense> allExpense = expenseRepo.findByUser(user);
        List<Expense> monthlyExpense = new ArrayList<>();

        LocalDate currentDate = LocalDate.now(ZoneId.of("Asia/Kolkata"));

        LocalDate lastWeekDate = currentDate.minusMonths(1);
        for (Expense ex : allExpense) {
            if (ex.getExpenseDateStamp().isEqual(currentDate)||ex.getExpenseDateStamp().isBefore(currentDate) && ex.getExpenseDateStamp().isAfter(lastWeekDate)) {
                monthlyExpense.add(ex);
            }
        }
        return monthlyExpense;
    }

    public List<Expense> getExpenseByDate(User user,LocalDate fromDate, LocalDate toDate) {
        List<Expense> allExpense = expenseRepo.findByUser(user);
        List<Expense> expens = new ArrayList<>();
        for (Expense ex : allExpense) {
            if ((ex.getExpenseDateStamp().isEqual(fromDate) || ex.getExpenseDateStamp().isEqual(toDate)) || ex.getExpenseDateStamp().isBefore(fromDate) && ex.getExpenseDateStamp().isAfter(toDate)) {
                expens.add(ex);
            }
        }
        return expens;
    }

    public String deleteExpenseById(User user,Long expenseId) {
        Expense expense =expenseRepo.findById(expenseId).orElse(null);
        if(expense==null){
            return "invalid credentials";
        }
        if(!expense.getUser().equals(user)){
            return "invalid credentials";
        }
        expenseRepo.deleteById(expenseId);
        return "expense deleted for Id : "+expenseId;
    }

    public List<Expense> findByUser(User user) {
        return expenseRepo.findByUser(user);
    }

    public void deleteExpenses(List<Expense> expenses) {
        expenseRepo.deleteAll(expenses);
    }
}
