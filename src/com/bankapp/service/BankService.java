package com.bankapp.service;

import com.bankapp.model.BankAccount;
import com.bankapp.repository.AccountRepository;

import java.util.Scanner;

public class BankService{

    private final BankAccount account;
    private final Scanner userInput;
    private final AccountRepository accountRepository;

    public BankService(BankAccount account, Scanner userInput, AccountRepository accountRepository) {
        this.account = account;
        this.userInput = userInput;
        this.accountRepository = accountRepository;
    }


    public void deposit() {
        System.out.print("Enter the amount you want to deposit: ");
        double depositAmount = userInput.nextDouble();
        validatePositiveAmount(depositAmount);
        account.setBalance(account.getBalance() + depositAmount);
        System.out.print("Your balance is now: $" + account.getBalance());

        accountRepository.updateBalance(account.getOwnerName(), account.getBalance());
    }

    public void withdraw() {
        System.out.print("Enter the amount you want to withdraw: ");
        double withdrawAmount = userInput.nextDouble();
        withdrawValidation(withdrawAmount);
        account.setBalance(account.getBalance() - withdrawAmount);
        System.out.print("Your balance is now: $" + account.getBalance());

        accountRepository.updateBalance(account.getOwnerName(), account.getBalance());
    }

    public void transfer() {
        System.out.println("Enter the amount you want to transfer: ");
        double transferAmount = userInput.nextDouble();
        userInput.nextLine();
        transferValidation(transferAmount);
        System.out.println("Enter the account you want to transfer your money to: ");
        String transferName = userInput.nextLine();
        BankAccount recipient = accountRepository.findByName(transferName);
        if (recipient == null) {
            System.out.println("Account not found.");
            return;
        }
        double newBalance = recipient.getBalance() + transferAmount;
        boolean success = accountRepository.updateBalance(transferName, newBalance);
        if (success) {
            System.out.println("Money transferred successfully!");
            account.setBalance(account.getBalance() - transferAmount);
            accountRepository.updateBalance(account.getOwnerName(), account.getBalance());
        }
    }


    private void validatePositiveAmount(Double depositAmount){
        if (depositAmount <= 0) {
            throw new IllegalArgumentException("Must be positive");
        }
    }

    private void withdrawValidation(Double withdrawAmount){
        validatePositiveAmount(withdrawAmount);
        if (withdrawAmount > account.getBalance()) {
            throw new IllegalArgumentException("Insufficient funds");
        }
    }

    private void transferValidation(Double transferAmount) {
        validatePositiveAmount(transferAmount);
        if (transferAmount > account.getBalance()) {
            throw new IllegalArgumentException("Insufficient funds");
        }
    }
}
