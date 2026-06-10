import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankService{

    private final BankAccount account;
    private final Scanner userInput;

    public BankService(BankAccount account, Scanner userInput) {
        this.account = account;
        this.userInput = userInput;
    }


    public void deposit() {
        System.out.print("Enter the amount you want to deposit: ");
        double depositAmount = userInput.nextDouble();
        depositValidation(depositAmount);
        account.setBalance(account.getBalance() + depositAmount);
        System.out.print("Your balance is now: $" + account.getBalance());
        try {
            saveBalance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void withdraw() {
        System.out.print("Enter the amount you want to withdraw: ");
        double withdrawAmount = userInput.nextDouble();
        withdrawValidation(withdrawAmount, account.getBalance());
        account.setBalance(account.getBalance() - withdrawAmount);
        System.out.print("Your balance is now: $" + account.getBalance());
        try {
            saveBalance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void transfer() {
        System.out.println("Enter the amount you want to transfer: ");
        double transferAmount = userInput.nextDouble();
        userInput.nextLine();
        transferValidation(transferAmount);
        System.out.println("Enter the account you want to transfer your money to: ");
        String transferName = userInput.nextLine();
        double newTransferredBalance = 0;
        boolean accountFound = false;
        List<String> lines = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader("database.txt"))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (transferName.equals(parts[0])) {
                    newTransferredBalance = Double.parseDouble(parts[1]) + transferAmount;
                    accountFound = true;
                    lines.add(parts[0] + "," + newTransferredBalance);
                    System.out.println("Money transferred successfully!");
                } else {
                    lines.add(line);
                }

            }
            if (!accountFound) {
                System.out.println("Account not found.");
                throw new IOException();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

        try (FileWriter writer = new FileWriter("database.txt", false)) {
            for (String line : lines) {
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        account.setBalance(account.getBalance() - transferAmount);
        try {
            saveBalance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void depositValidation(Double depositAmount){
        if (depositAmount <= 0) {
            throw new IllegalArgumentException("Must be positive");
        }
    }

    private void withdrawValidation(Double withdrawAmount, double balance){
        if (withdrawAmount <= 0) {
            throw new IllegalArgumentException("Must be positive");
        }
        if (withdrawAmount > account.getBalance()) {
            throw new IllegalArgumentException("Insufficient funds");
        }
    }

    private void transferValidation(Double transferAmount) {
        if (transferAmount <= 0) {
            throw new IllegalArgumentException("Must be positive");
        } else if (transferAmount > account.getBalance()) {
            throw new IllegalArgumentException("Insufficient funds");
        }
    }

    public void saveBalance() throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("database.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (account.getOwnerName().equals(parts[0])) {
                    lines.add(parts[0] + "," + account.getBalance());
                } else {
                    lines.add(line);
                }
            }
        }
        try (FileWriter writer = new FileWriter("database.txt", false)) {
            for (String line : lines) {
                writer.write(line + "\n");
            }
        }
    }
}
