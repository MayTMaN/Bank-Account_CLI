package com.bankapp.service;

import com.bankapp.model.BankAccount;
import com.bankapp.repository.AccountRepository;

import javax.security.auth.login.LoginException;
import java.util.Scanner;

public class AuthService{

    private boolean isAuthorized = false;
    private final BankAccount account;
    private final Scanner userInput;
    private final AccountRepository accountRepository;

    public AuthService(BankAccount account, Scanner userInput, AccountRepository accountRepository) {
        this.account = account;
        this.userInput = userInput;
        this.accountRepository = accountRepository;
    }

    public void setAuthorized (boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    public boolean isAuthorized () {
        return isAuthorized;
    }


    public void loggingIn () throws LoginException {
        System.out.print("Enter your name: ");
        String name = userInput.nextLine();
        BankAccount found = accountRepository.findByName(name);
        if(found != null) {
            System.out.println("You've successfully logged in!");
            account.setOwnerName(found.getOwnerName());
            account.setBalance(found.getBalance());
            isAuthorized = true;
        } else {
            System.out.println("Account " + name  + " doesnt exist.");
            throw new LoginException();
        }

    }

    public void signingUp () throws LoginException {
        System.out.print("Enter your name: ");
        String name = userInput.nextLine();

        if (accountRepository.findByName(name) != null) {
            System.out.println("This account is already registered.");
            throw new LoginException();
        }

        account.setOwnerName(name);
        isAuthorized = true;

        accountRepository.saveInfo(name);
        System.out.println("You have successfully registered!");
    }

}
