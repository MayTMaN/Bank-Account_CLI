package com.bankapp;

import com.bankapp.model.BankAccount;
import com.bankapp.repository.AccountRepository;
import com.bankapp.service.AuthService;
import com.bankapp.service.BankService;
import com.bankapp.ui.UserInterface;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.util.Scanner;

import static com.bankapp.service.util.Constants.DB_FILE;

public class Main {

    public static void main(String[] args) throws IOException {

        File dataBase = new File(DB_FILE);
        if (!dataBase.exists()) {
            dataBase.createNewFile();
        }
        Scanner userInput = new Scanner(System.in);
        BankAccount account = new BankAccount();
        AccountRepository accountRepository = new AccountRepository();
        BankService bankService = new BankService(account,userInput,accountRepository);
        AuthService authService = new AuthService(account,userInput,accountRepository);
        UserInterface userInterface = new UserInterface();

        if (!authService.isAuthorized()) {
            userInterface.authorizationMenu();
            int choice = userInput.nextInt();
            userInput.nextLine();
            switch (choice) {
                case 1 -> {
                    try {
                        authService.loggingIn();
                    }catch (LoginException e){
                        System.out.println("Turning off application....");
                        return;
                    }
                }

                case 2 -> {
                    try {
                        authService.signingUp();
                    } catch (LoginException e) {
                        System.out.println("Turning off application....");
                        return;
                    }
                }

                default -> {
                    System.out.println("Option " + choice + " doesn't exist.");
                    return;
                }
            }
        }


        userInterface.mainMenu();
        int choice = userInput.nextInt();
        userInput.nextLine();

        switch (choice) {
            case 1 -> {
                bankService.deposit();
            }

            case 2 -> {
                bankService.withdraw();
            }

            case 3 -> {
                bankService.transfer();
            }

            default -> {
                System.out.println("Option " + choice + " doesn't exist.");
                return;
            }
        }
    }

}
