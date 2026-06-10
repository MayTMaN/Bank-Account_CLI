import java.util.Scanner;

public class UserInterface{

    public void mainMenu () {
        System.out.println("****************");
        System.out.println("BANK APP");
        System.out.println("****************");
        System.out.println("OPTIONS");
        System.out.println("| 1. Deposit");
        System.out.println("| 2. Withdraw");
        System.out.println("| 3. Transfer");
        System.out.println("| 4. View transaction history");
        System.out.print("Choose an option (1-4): ");
    }

    public void authorizationMenu () {
        System.out.println("| 1. Log in");
        System.out.println("| 2. Sign up");
        System.out.print("Choose an option (1-2): ");
    }

}
