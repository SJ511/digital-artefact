import java.io.*;
import java.util.*;

public class BookingManager {
    private AccountManager accountManager;
    private DestinationManager destinationManager;
    private Map<String, List<String>> bookings;

    public BookingManager(AccountManager accountManager, DestinationManager destinationManager) {
        this.accountManager = accountManager;
        this.destinationManager = destinationManager;
        this.bookings = new HashMap<>();
        loadBookings();
    }

    private void loadBookings() {
        try (Scanner scanner = new Scanner(new File("bookings.csv"))) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                bookings.computeIfAbsent(data[0], k -> new ArrayList<>()).add(data[1] + " on " + data[2] + " class for " + data[3]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Booking file not found, starting fresh.");
        }
    }

    private void saveBookings() {
        try (PrintWriter out = new PrintWriter("bookings.csv")) {
            for (Map.Entry<String, List<String>> entry : bookings.entrySet()) {
                for (String detail : entry.getValue()) {
                    out.println(entry.getKey() + "," + detail);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error saving bookings.");
        }
    }

    public void handleBooking(Account account, Scanner scanner) {
        System.out.println("Do you want to (1) book a flight, (2) view your bookings?");
        int choice = scanner.nextInt();
        if (choice == 1) {
            bookFlight(account, scanner);
        } else {
            viewBookings(account);
        }
    }

    private void bookFlight(Account account, Scanner scanner) {
        destinationManager.showDestinations();
        Destination destination = destinationManager.chooseDestination(scanner);

        System.out.println("Available classes: 1. Economy, 2. Business, 3. First");
        System.out.print("Choose class (1-3): ");
        int classChoice = scanner.nextInt();
        String flightClass = classChoice == 1 ? "economy" : classChoice == 2 ? "business" : "first";

        double price = destination.calculateTotalPrice(flightClass);
        System.out.println("Your booking to " + destination.getName() + " in " + flightClass + " class will cost: £" + price);
        System.out.print("Confirm booking? (yes/no): ");
        String confirm = scanner.next();
        if (confirm.equalsIgnoreCase("yes")) {
            bookings.computeIfAbsent(account.getUsername(), k -> new ArrayList<>())
                    .add(destination.getName() + "," + flightClass + "," + price);
            saveBookings();
            System.out.println("Booking confirmed and saved.");
        } else {
            System.out.println("Booking cancelled.");
        }
    }

    private void viewBookings(Account account) {
        List<String> userBookings = bookings.get(account.getUsername());
        if (userBookings != null && !userBookings.isEmpty()) {
            System.out.println("Your bookings:");
            userBookings.forEach(System.out::println);
        } else {
            System.out.println("No bookings found.");
        }
    }
}