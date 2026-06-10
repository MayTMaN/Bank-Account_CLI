import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AuthService{

    Scanner userInput = new Scanner(System.in);

    private final BankAccount account;

    public AuthService(BankAccount account) {
        this.account = account;
    }

    public void loggingIn () throws LoginException {
        System.out.print("Enter your name: ");
        String name = userInput.nextLine();
        try (BufferedReader fileReader = new BufferedReader(new FileReader("database.txt"))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                if (name.equals(line)) {
                    System.out.println("You've successfully logged in!");
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

    public void signingUp () {
        System.out.print("Enter your name: ");
        String name = account.userInput.nextLine();
        account.setOwnerName(name);
        try (BufferedReader fileReader = new BufferedReader(new FileReader("database.txt"))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                if (name.equals(line)) {
                    throw new IllegalArgumentException("This account is already registered.");
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
        account.setAuthorized(true);
        try (FileWriter fileWriter = new FileWriter("database.txt", true)) {
            fileWriter.write(account.getOwnerName() + "\n");
            System.out.println("You have successfully registered!");
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

}
