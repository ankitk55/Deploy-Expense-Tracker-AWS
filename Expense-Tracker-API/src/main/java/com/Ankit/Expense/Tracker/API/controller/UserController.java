package com.Ankit.Expense.Tracker.API.controller;


import com.Ankit.Expense.Tracker.API.model.Expense;
import com.Ankit.Expense.Tracker.API.model.Product;
import com.Ankit.Expense.Tracker.API.model.User;
import com.Ankit.Expense.Tracker.API.model.dto.SignInInput;
import com.Ankit.Expense.Tracker.API.model.dto.SignUpOutput;
import com.Ankit.Expense.Tracker.API.service.AuthenticationService;
import com.Ankit.Expense.Tracker.API.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Validated
@RestController
public class UserController {
    @Autowired
    UserService userService;


@GetMapping("user/profile")
public User getUser(@RequestParam @Validated String email,@RequestParam String token){
    return userService.getUser(email,token);

}
    @PostMapping("user/signup")
    public SignUpOutput signUpPatient(@RequestBody User user)
    {

        return userService.signUpUser(user);
    }

    @PostMapping("user/signIn")
    public String sigInPatient(@RequestBody @Valid SignInInput signInInput)
    {
        return userService.signInUser(signInInput);
    }

    @DeleteMapping("user/signOut")
    public String sigOutPatient(String email, String token)
    {

            return userService.sigOutUser(email,token);
        }




@PostMapping("expense")
public ResponseEntity<String>addExpense(@RequestParam @Valid String email,@RequestParam String token,@RequestBody Expense expense){
        return userService.addExpense(email,token,expense);
}

    @DeleteMapping("user/account")
    public String deleteUserAccount(@RequestParam @Valid String email,@RequestParam String token){

            return userService.deleteUserAccount(email,token);
        }


    @GetMapping("last/week/expenses")
    public List<Expense> weeklyExpenses(@RequestParam @Valid String email,@RequestParam String token)  {
        return userService.weeklyExpenses(email,token);
    }
    @GetMapping("last/month/expenses")
    public  List<Expense> monthlyExpenses(@RequestParam @Valid String email,@RequestParam String token){
        return userService.monthlyExpenses(email,token);
    }

    @PostMapping("product")
    public String addProduct(@RequestParam @Valid String email,@RequestParam String token,@RequestBody Product product){
       return  userService.addProduct(email,token,product);
    }

    @GetMapping("expenses/date/from/{fromDate}/to/{toDate}")
    public  List<Expense> getExpenseByDate(@RequestParam @Valid String email,@RequestParam String token,@PathVariable LocalDate fromDate, @PathVariable LocalDate toDate){
        return userService.getExpenseByDate(email,token,fromDate,toDate);
    }
    @GetMapping("product/date/{date}")
    public List<Product>getProductByDate(@RequestParam @Valid String email,@RequestParam String token,@PathVariable LocalDate date){
        return userService.getProductByDate(email,token,date);
    }
    @DeleteMapping("expense/{expenseId}")
    public String deleteExpenseById(@RequestParam @Valid String email,@RequestParam String token,@PathVariable Long expenseId){
        return userService.deleteExpenseById(email,token,expenseId);
    }
    @PutMapping("user/email/{newEmail}")
    public String updateUserEmail(@PathVariable String newEmail,@RequestParam @Valid String email, @RequestParam String token){
        return userService.updateUserEmail(newEmail,email,token);
    }
    @PutMapping("user/phone/{newPhoneNumber}")
    public String updateUserPhone(@PathVariable String newPhoneNumber,@RequestParam @Valid String email, @RequestParam String token){
        return userService.updateUserPhone(newPhoneNumber,email,token);
    }
    @PutMapping("user/password/{newPassword}")
    public String updatePassword(@PathVariable @Valid String newPassword,@RequestParam @Valid String email, @RequestParam String token){
        return userService.updatePassword(newPassword,email,token);
    }
}
