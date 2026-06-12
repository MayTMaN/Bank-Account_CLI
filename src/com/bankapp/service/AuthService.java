package com.bankapp.service;

import com.bankapp.model.BankAccount;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AuthService{

    private final BankAccount account;
    private final Scanner userInput;

    public AuthService(BankAccount account, Scanner userInput) {
        this.account = account;
        this.userInput = userInput;
    }

    public void loggingIn () throws LoginException {
        System.out.print("Enter your name: ");
        String name = userInput.nextLine();
        try (BufferedReader fileReader = new BufferedReader(new FileReader("database.txt"))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (name.equals(parts[0])) {
                    System.out.println("You've successfully logged in!");
                    account.setOwnerName(name);
                    account.setBalance(Double.parseDouble(parts[1]));
                    account.setAuthorized(true);
                    break;
                }
            }
            if(!account.isAuthorized()){
                System.out.println("Account " + name  + " doesnt exist.");
                throw new LoginException();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            throw new LoginException();
        }
    }

    public void signingUp () throws LoginException {
        System.out.print("Enter your name: ");
        String name = userInput.nextLine();
        account.setOwnerName(name);
        try (BufferedReader fileReader = new BufferedReader(new FileReader("database.txt"))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (name.equals(parts[0])) {
                    System.out.println("This account is already registered.");
                    throw new LoginException();
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            throw new LoginException();
        }
        account.setAuthorized(true);
        try (FileWriter fileWriter = new FileWriter("database.txt", true)) {
            fileWriter.write(account.getOwnerName() + ",0.0\n");
            System.out.println("You have successfully registered!");
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

}
