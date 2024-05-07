import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AccountManager accountManager = new AccountManager();
        DestinationManager destinationManager = new DestinationManager();
        BookingManager bookingManager = new BookingManager(accountManager, destinationManager);

        System.out.println("Hi, welcome to SJ Airlines! Would you like to (1) make an account or (2) log into an existing account?");
        int choice = scanner.nextInt();
        Account currentAccount;
        if (choice == 1) {
            currentAccount = accountManager.createAccount(scanner);
        } else {
            currentAccount = accountManager.login(scanner);
        }

        if (currentAccount != null) {
            bookingManager.handleBooking(currentAccount, scanner);
        }
    }
}
