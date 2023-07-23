package com.Ankit.Expense.Tracker.API.service;

import com.Ankit.Expense.Tracker.API.model.AuthenticationToken;
import com.Ankit.Expense.Tracker.API.repository.IAuthenticationTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    IAuthenticationTokenRepo authTokenRepo;

    public boolean authenticate(String email, String authTokenValue)
    {
        AuthenticationToken authToken = authTokenRepo.findFirstByTokenValue(authTokenValue);

        if(authToken == null)
        {
            return false;
        }

        String tokenConnectedEmail = authToken.getUser().getUserEmail();

        return tokenConnectedEmail.equals(email);
    }


    public void addToken(AuthenticationToken authToken) {
        authTokenRepo.save(authToken);
    }

    public void deleteToken(String token) {
       AuthenticationToken authToken = authTokenRepo.findFirstByTokenValue(token);
       authTokenRepo.delete(authToken);
    }
}
