import javax.security.auth.login.LoginException;
import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        File dataBase = new File("database.txt");
        File transactionHistory = new File("transaction_history.txt");
        if (!dataBase.exists() || !transactionHistory.exists()) {
            dataBase.createNewFile();
            transactionHistory.createNewFile();
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

                case 3 -> {

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
        }
    }

}
