
import java.io.*;
import java.util.*;

public class HotelManagementSystem {
    static Scanner sc = new Scanner(System.in);
    static final String FILE_NAME = "guests.txt";

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n--- Hotel Management System ---");
            System.out.println("1. Book Room");
            System.out.println("2. Cancel Room");
            System.out.println("3. Check Availability");
            System.out.println("4. View Guests");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();  // consume newline

            switch (choice) {
                case 1: bookRoom(); break;
                case 2: cancelRoom(); break;
                case 3: checkAvailability(); break;
                case 4: viewGuests(); break;
                case 5: System.out.println("Thank you!"); break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 5);
    }

    static void bookRoom() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            System.out.print("Enter guest name: ");
            String name = sc.nextLine();
            System.out.print("Enter room number: ");
            String room = sc.nextLine();
            bw.write(name + "," + room);
            bw.newLine();
            System.out.println("Room booked successfully!");
        } catch (IOException e) {
            System.out.println("Error booking room: " + e.getMessage());
        }
    }

    static void cancelRoom() {
        System.out.print("Enter room number to cancel: ");
        String roomToCancel = sc.nextLine();
        File inputFile = new File(FILE_NAME);
        File tempFile = new File("temp.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (!data[1].equals(roomToCancel)) {
                    bw.write(line);
                    bw.newLine();
                } else {
                    found = true;
                }
            }
            if (found) {
                System.out.println("Room cancelled successfully!");
            } else {
                System.out.println("Room not found!");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
            System.out.println("Error updating room records.");
        }
    }

    static void checkAvailability() {
        System.out.print("Enter room number to check: ");
        String roomToCheck = sc.nextLine();
        boolean available = true;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].equals(roomToCheck)) {
                    available = false;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading records: " + e.getMessage());
        }

        if (available) {
            System.out.println("Room is available.");
        } else {
            System.out.println("Room is already booked.");
        }
    }

    static void viewGuests() {
        System.out.println("\n--- Guest List ---");
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                System.out.println("Guest: " + data[0] + ", Room: " + data[1]);
            }
        } catch (IOException e) {
            System.out.println("Error reading guest records: " + e.getMessage());
        }
    }
}
