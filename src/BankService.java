import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
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
    }

    public void withdraw() {
        System.out.print("Enter the amount you want to withdraw: ");
        double withdrawAmount = userInput.nextDouble();
        withdrawValidation(withdrawAmount, account.getBalance());
        account.setBalance(account.getBalance() - withdrawAmount);
        System.out.print("Your balance is now: $" + account.getBalance());
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
}
