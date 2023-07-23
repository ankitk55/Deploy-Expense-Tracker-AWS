package com.Ankit.Expense.Tracker.API.service;

import com.Ankit.Expense.Tracker.API.model.Expense;
import com.Ankit.Expense.Tracker.API.model.Product;
import com.Ankit.Expense.Tracker.API.service.utility.EmailHandler;
import com.Ankit.Expense.Tracker.API.service.utility.PasswordEncrypter;
import com.Ankit.Expense.Tracker.API.model.AuthenticationToken;

import com.Ankit.Expense.Tracker.API.model.User;
import com.Ankit.Expense.Tracker.API.model.dto.SignInInput;
import com.Ankit.Expense.Tracker.API.model.dto.SignUpOutput;
import com.Ankit.Expense.Tracker.API.repository.IAuthenticationTokenRepo;
import com.Ankit.Expense.Tracker.API.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    IUserRepo userRepo;
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    IAuthenticationTokenRepo authTokenRepo;
    @Autowired
    ExpenseService expenseService;
@Autowired
ProductService productService;





    public SignUpOutput signUpUser(User user) {

        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        String newEmail = user.getUserEmail();

        if (newEmail == null) {
            signUpStatusMessage = "Invalid email";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }

        //check if this User email already exists ??
        User existingUser = userRepo.findFirstByUserEmail(newEmail);

        if (existingUser != null) {
            signUpStatusMessage = "Email already registered!!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(user.getUserPassword());

            //saveAppointment the User with the new encrypted password
            user.setUserCreationTimeStamp(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
            user.setUserPassword(encryptedPassword);
            userRepo.save(user);

            return new SignUpOutput(signUpStatus, "User registered successfully!!!");
        } catch (Exception e) {
            signUpStatusMessage = "Internal error occurred during sign up";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }

    }

    public String signInUser(SignInInput signInInput) {


        String signInStatusMessage = null;

        String signInEmail = signInInput.getEmail();

        if (signInEmail == null) {
            signInStatusMessage = "Invalid email";
            return signInStatusMessage;


        }

        //check if this User email already exists ??
        User existingUser = userRepo.findFirstByUserEmail(signInEmail);

        if (existingUser == null) {
            signInStatusMessage = "Email not registered!!!";
            return signInStatusMessage;

        }

        //match passwords :

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if (existingUser.getUserPassword().equals(encryptedPassword)) {
                //session should be created since password matched and user id is valid
                AuthenticationToken authToken = new AuthenticationToken(existingUser);
                authTokenRepo.save(authToken);

                EmailHandler.sendEmail(signInEmail, "Token for Verify Identity !!!", authToken.getTokenValue());
                return "Token sent to your email";
            } else {
                signInStatusMessage = "Invalid credentials!!!";
                return signInStatusMessage;
            }
        } catch (Exception e) {
            signInStatusMessage = "Internal error occurred during sign in";
            return signInStatusMessage;
        }

    }

    public String sigOutUser(String email,String token) {
        if(authenticationService.authenticate(email,token)) {
            User user = userRepo.findFirstByUserEmail(email);
            authTokenRepo.delete(authTokenRepo.findFirstByUser(user));
            return "User Signed out successfully";
        }
        return "Sign out not allowed for non authenticated user.";
    }






    public String deleteUserAccount(String email,String token) {
        if (authenticationService.authenticate(email, token)) {
            sigOutUser(email,token);
            User user = userRepo.findFirstByUserEmail(email);
            List<Expense>expenses =expenseService.findByUser(user);
            expenseService.deleteExpenses(expenses);
            List<Product>products  =productService.findProductByUser(user);
            productService.deleteProducts(products);

            userRepo.delete(user);
            return "user Account deleted Successfully !!!";
        }
        return "invalid credentials";
    }



    public ResponseEntity<String> addExpense(String email, String token,Expense expense) {
        if(authenticationService.authenticate(email,token)) {
            User user = userRepo.findFirstByUserEmail(email);
            return expenseService.addExpense(user, expense);
        }
        else{
            return ResponseEntity.ok("invalid credentials");
        }
    }
    public List<Expense> weeklyExpenses(String email,String token)  {
        if(authenticationService.authenticate(email,token)){
            User user = userRepo.findFirstByUserEmail(email);
            return expenseService.weeklyExpense(user);

        }
         return null;
    }

    public String addProduct(String email, String token,Product product) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
           return productService.addProduct(user,product);

        }
        return "invalid credentials";
    }

    public List<Expense> monthlyExpenses(String email,String token) {
        if(authenticationService.authenticate(email,token)) {
            User user = userRepo.findFirstByUserEmail(email);
            return expenseService.monthlyExpenses(user);
        }
        return null;
    }

    public List<Expense> getExpenseByDate(String email, String token,LocalDate fromDate, LocalDate toDate) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            return expenseService.getExpenseByDate(user,fromDate,toDate);

        }
        return null;
    }

    public List<Product> getProductByDate(String email, String token, LocalDate date) {
        if(authenticationService.authenticate(email,token)) {
            User user =userRepo.findFirstByUserEmail(email);
            return productService.getProductByDate(user,date);
        }
        return null;
    }

    public String deleteExpenseById(String email,String token,Long expenseId) {
        if(authenticationService.authenticate(email,token)) {
            User user = userRepo.findFirstByUserEmail(email);
            return expenseService.deleteExpenseById(user,expenseId);
        }
        return null;
    }
    public String updateUserEmail(String newEmail, String email, String token) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            user.setUserEmail(newEmail);
            userRepo.save(user);
            return "email changed ..";
        }
        return "invalid credentials";
    }

    public String updateUserPhone(String newPhoneNumber, String email, String token) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            user.setUserPhoneNumber(newPhoneNumber);
            userRepo.save(user);
            return "phone number changed ..";
        }
        return "invalid credentials";
    }

    public String updatePassword(String newPassword, String email, String token) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            try {
                String encryptedPassword = PasswordEncrypter.encryptPassword(newPassword);
                user.setUserPassword(encryptedPassword);
                userRepo.save(user);
                return "password  changed ..";
            }
            catch(Exception e){
                System.out.println("some error occurred during creating a Hash code of Password ");
            }
        }
        return "invalid credentials";
    }

    public User getUser(String email, String token) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            return user;
        }
        return null;
    }
}





