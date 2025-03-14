import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class DisplayBookingFrame extends JFrame {

    private JTable bookingTable;
    private DefaultTableModel tableModel;
    private ArrayList<String[]> bookings;

    // Path to the bookings CSV file
    private static final String BOOKINGS_FILE = "bookings.csv";

    // Color scheme for a professional look
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);  // Steel Blue
    private static final Color BUTTON_COLOR = new Color(30, 144, 255);   // Dodger Blue
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245); // Light Gray
    private static final Color HEADER_COLOR = new Color(60, 60, 60);    // Dark Gray

    public DisplayBookingFrame() {
        setTitle("Booking Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize bookings by loading from the CSV file
        bookings = loadBookings();

        // Create table model and set column names
        String[] columnNames = {"Order No", "Name", "Address", "Phone", "Destination"};
        tableModel = new DefaultTableModel(columnNames, 0);

        // Fill the table with loaded data
        for (String[] booking : bookings) {
            tableModel.addRow(booking);
        }

        // JTable for displaying bookings
        bookingTable = new JTable(tableModel);
        bookingTable.setBackground(Color.WHITE);
        bookingTable.setForeground(Color.BLACK);
        bookingTable.setFont(new Font("Arial", Font.PLAIN, 14));
        bookingTable.getTableHeader().setBackground(HEADER_COLOR);
        bookingTable.getTableHeader().setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(bookingTable);
        scrollPane.setPreferredSize(new Dimension(580, 300));
        add(scrollPane, BorderLayout.CENTER);

        // Panel for Buttons (CRUD operations)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addBtn = new JButton("Add Booking");
        addBtn.setBackground(BUTTON_COLOR);
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);
        addBtn.addActionListener(e -> showAddBookingDialog());

        JButton editBtn = new JButton("Edit Booking");
        editBtn.setBackground(BUTTON_COLOR);
        editBtn.setForeground(Color.WHITE);
        editBtn.setFocusPainted(false);
        editBtn.addActionListener(e -> showEditBookingDialog());

        JButton deleteBtn = new JButton("Delete Booking");
        deleteBtn.setBackground(BUTTON_COLOR);
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);
        deleteBtn.addActionListener(e -> deleteBooking());

        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set background color for the main frame
        getContentPane().setBackground(BACKGROUND_COLOR);

        setVisible(true);
    }

    // Load bookings from file (with file existence check)
    private ArrayList<String[]> loadBookings() {
        ArrayList<String[]> bookingsList = new ArrayList<>();
        File file = new File(BOOKINGS_FILE);

        // Check if the file exists, if not create it
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Read bookings from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                bookingsList.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bookingsList;
    }

    // Save bookings to file
    private void saveBookings() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKINGS_FILE))) {
            for (String[] booking : bookings) {
                writer.write(String.join(",", booking));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Show Add Booking dialog
    private void showAddBookingDialog() {
        JTextField orderNoField = new JTextField(10);
        JTextField nameField = new JTextField(10);
        JTextField addressField = new JTextField(10);
        JTextField phoneField = new JTextField(10);
        JTextField destinationField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.setBackground(BACKGROUND_COLOR);
        panel.add(new JLabel("Order No:"));
        panel.add(orderNoField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Destination:"));
        panel.add(destinationField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Add New Booking", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String[] newBooking = {
                    orderNoField.getText(),
                    nameField.getText(),
                    addressField.getText(),
                    phoneField.getText(),
                    destinationField.getText()
            };

            bookings.add(newBooking);
            tableModel.addRow(newBooking);
            saveBookings(); // Save the updated bookings to file
        }
    }

    // Show Edit Booking dialog
    private void showEditBookingDialog() {
        int selectedRow = bookingTable.getSelectedRow();
        if (selectedRow != -1) {
            String orderNo = (String) tableModel.getValueAt(selectedRow, 0);
            String name = (String) tableModel.getValueAt(selectedRow, 1);
            String address = (String) tableModel.getValueAt(selectedRow, 2);
            String phone = (String) tableModel.getValueAt(selectedRow, 3);
            String destination = (String) tableModel.getValueAt(selectedRow, 4);

            JTextField orderNoField = new JTextField(orderNo);
            JTextField nameField = new JTextField(name);
            JTextField addressField = new JTextField(address);
            JTextField phoneField = new JTextField(phone);
            JTextField destinationField = new JTextField(destination);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(5, 2));
            panel.setBackground(BACKGROUND_COLOR);
            panel.add(new JLabel("Order No:"));
            panel.add(orderNoField);
            panel.add(new JLabel("Name:"));
            panel.add(nameField);
            panel.add(new JLabel("Address:"));
            panel.add(addressField);
            panel.add(new JLabel("Phone:"));
            panel.add(phoneField);
            panel.add(new JLabel("Destination:"));
            panel.add(destinationField);

            int option = JOptionPane.showConfirmDialog(this, panel, "Edit Booking", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String[] updatedBooking = {
                        orderNoField.getText(),
                        nameField.getText(),
                        addressField.getText(),
                        phoneField.getText(),
                        destinationField.getText()
                };

                bookings.set(selectedRow, updatedBooking);
                tableModel.setValueAt(orderNoField.getText(), selectedRow, 0);
                tableModel.setValueAt(nameField.getText(), selectedRow, 1);
                tableModel.setValueAt(addressField.getText(), selectedRow, 2);
                tableModel.setValueAt(phoneField.getText(), selectedRow, 3);
                tableModel.setValueAt(destinationField.getText(), selectedRow, 4);

                saveBookings(); // Save the updated bookings to file
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a booking to edit!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Delete selected booking
    private void deleteBooking() {
        int selectedRow = bookingTable.getSelectedRow();
        if (selectedRow != -1) {
            String orderNo = (String) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete booking with Order No: " + orderNo + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                bookings.remove(selectedRow);
                tableModel.removeRow(selectedRow);
                saveBookings(); // Save the updated bookings to file
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a booking to delete!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DisplayBookingFrame());
    }
}
