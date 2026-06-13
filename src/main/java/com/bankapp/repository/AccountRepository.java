package com.bankapp.repository;

import com.bankapp.model.BankAccount;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.bankapp.service.util.Constants.DB_FILE;
import static com.bankapp.service.util.Constants.STARTING_BALANCE;

public class AccountRepository {

    public BankAccount findByName(String name) {
        try (BufferedReader fileReader = new BufferedReader(new java.io.FileReader(DB_FILE))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (name.equals(parts[0])) {
                    BankAccount found = new BankAccount();
                    found.setOwnerName(parts[0]);
                    found.setBalance(Double.parseDouble(parts[1]));
                    return found;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
        return null;
    }

    public void saveInfo(String ownerName) {
        try (FileWriter fileWriter = new FileWriter(DB_FILE, true)) {
            fileWriter.write(ownerName + "," + STARTING_BALANCE + "\n");
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public boolean updateBalance(String name, double newBalance) {
        boolean accountFound = false;
        List<String> lines = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(DB_FILE))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (name.equals(parts[0])) {
                    lines.add(parts[0] + "," + newBalance);
                    accountFound = true;
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

        try (FileWriter writer = new FileWriter(DB_FILE, false)) {
            for (String line : lines) {
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return accountFound;
    }
}
