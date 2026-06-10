import javax.security.auth.login.LoginException;
import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        File dataBase = new File("database.txt");
        if (!dataBase.exists()) {
            dataBase.createNewFile();
        }
        Scanner userInput = new Scanner(System.in);
        BankAccount account = new BankAccount();
        BankService bankService = new BankService(account);
        AuthService authService = new AuthService(account);
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
                    authService.signingUp();
                }

            }
        }


        userInterface.mainMenu();
        int choice = userInput.nextInt();
        userInput.nextLine();

        switch (choice) {
            case 1 -> {
                bankService.deposit(account.getBalance());
            }

            case 2 -> {
                bankService.withdraw(account.getBalance());
            }
        }
    }

}
