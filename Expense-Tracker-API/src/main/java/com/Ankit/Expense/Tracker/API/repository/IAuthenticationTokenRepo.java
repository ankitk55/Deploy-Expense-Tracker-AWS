package com.Ankit.Expense.Tracker.API.repository;

import com.Ankit.Expense.Tracker.API.model.AuthenticationToken;
import com.Ankit.Expense.Tracker.API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthenticationTokenRepo extends JpaRepository<AuthenticationToken,Long> {
    AuthenticationToken findFirstByUser(User user);

    AuthenticationToken findFirstByTokenValue(String authTokenValue);



}
