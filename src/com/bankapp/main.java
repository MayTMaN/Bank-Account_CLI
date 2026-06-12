package com.bankapp;

import com.bankapp.model.BankAccount;
import com.bankapp.service.AuthService;
import com.bankapp.service.BankService;
import com.bankapp.ui.UserInterface;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.util.Scanner;

public class main {

    public static void main(String[] args) throws IOException {

        File dataBase = new File("database.txt");
        if (!dataBase.exists()) {
            dataBase.createNewFile();
        }
        Scanner userInput = new Scanner(System.in);
        BankAccount account = new BankAccount();
        BankService bankService = new BankService(account,userInput);
        AuthService authService = new AuthService(account,userInput);
        UserInterface userInterface = new UserInterface();

        if (!account.isAuthorized()) {
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
        }
    }

}
