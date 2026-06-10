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
