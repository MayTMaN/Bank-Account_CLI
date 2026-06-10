import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class BankService{

    Scanner userInput = new Scanner(System.in);

    private final BankAccount account;

    public BankService(BankAccount account) {
        this.account = account;
    }


    public void deposit(double balance) {
        System.out.print("Enter the amount you want to deposit: ");
        double depositAmount = userInput.nextDouble();
        depositValidation(depositAmount);
        account.setBalance(account.getBalance() + depositAmount);
        System.out.print("Your balance is now: $" + account.getBalance());
    }

    public void withdraw(double balance) {
        System.out.print("Enter the amount you want to withdraw: ");
        double withdrawAmount = userInput.nextDouble();
        withdrawValidation(withdrawAmount, balance);
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
