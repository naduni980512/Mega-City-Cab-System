import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class FileManager {
    static final String USERS_FILE = "users.txt";
    static final String BOOKINGS_FILE = "bookings.txt";
    static final String CARS_FILE = "cars.txt";
    static final String DRIVERS_FILE = "drivers.txt";

    public static void createRequiredFiles() {
        try {
            File users = new File(USERS_FILE);
            if (users.createNewFile()) {
                FileWriter fw = new FileWriter(users);
                fw.write("admin,admin123\nmanager,manager123\n");
                fw.close();
            }

            new File(BOOKINGS_FILE).createNewFile();
            new File(CARS_FILE).createNewFile();
            new File(DRIVERS_FILE).createNewFile();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error creating files: " + e.getMessage());
        }
    }

    public static boolean authenticateUser(String username, String password) {
        return (username.equals("admin") && password.equals("admin123")) ||
                (username.equals("manager") && password.equals("manager123"));
    }

    public static void appendToFile(String fileName, String data) {
        try (FileWriter fw = new FileWriter(fileName, true)) {
            fw.write(data + "\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing file!");
        }
    }

    public static String readFileContent(String fileName) {
        StringBuilder content = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            return "File not found!";
        }
        return content.toString();
    }

    public static void addBooking() {
    }
}
