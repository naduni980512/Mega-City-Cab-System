import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class ManageDriverFrame extends JFrame {

    private JTable driverTable;
    private DefaultTableModel tableModel;
    private ArrayList<String[]> drivers;

    // Path to the drivers CSV file
    private static final String DRIVERS_FILE = "drivers.csv";

    // Color scheme for a professional look
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);  // Steel Blue
    private static final Color BUTTON_COLOR = new Color(30, 144, 255);   // Dodger Blue
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245); // Light Gray
    private static final Color HEADER_COLOR = new Color(60, 60, 60);    // Dark Gray

    public ManageDriverFrame() {
        setTitle("Manage Driver Information");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize drivers by loading from the CSV file
        drivers = loadDrivers();

        // Create table model and set column names
        String[] columnNames = {"Driver ID", "Name", "License Number", "Phone", "Age"};
        tableModel = new DefaultTableModel(columnNames, 0);

        // Fill the table with loaded data
        for (String[] driver : drivers) {
            tableModel.addRow(driver);
        }

        // JTable for displaying drivers
        driverTable = new JTable(tableModel);
        driverTable.setBackground(Color.WHITE);
        driverTable.setForeground(Color.BLACK);
        driverTable.setFont(new Font("Arial", Font.PLAIN, 14));
        driverTable.getTableHeader().setBackground(HEADER_COLOR);
        driverTable.getTableHeader().setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(driverTable);
        scrollPane.setPreferredSize(new Dimension(580, 300));
        add(scrollPane, BorderLayout.CENTER);

        // Panel for Buttons (CRUD operations)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addBtn = new JButton("Add Driver");
        addBtn.setBackground(BUTTON_COLOR);
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);
        addBtn.addActionListener(e -> showAddDriverDialog());

        JButton editBtn = new JButton("Edit Driver");
        editBtn.setBackground(BUTTON_COLOR);
        editBtn.setForeground(Color.WHITE);
        editBtn.setFocusPainted(false);
        editBtn.addActionListener(e -> showEditDriverDialog());

        JButton deleteBtn = new JButton("Delete Driver");
        deleteBtn.setBackground(BUTTON_COLOR);
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);
        deleteBtn.addActionListener(e -> deleteDriver());

        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set background color for the main frame
        getContentPane().setBackground(BACKGROUND_COLOR);

        setVisible(true);
    }

    // Load drivers from file (with file existence check)
    private ArrayList<String[]> loadDrivers() {
        ArrayList<String[]> driversList = new ArrayList<>();
        File file = new File(DRIVERS_FILE);

        // Check if the file exists, if not create it
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Read drivers from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                driversList.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return driversList;
    }

    // Save drivers to file
    private void saveDrivers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DRIVERS_FILE))) {
            for (String[] driver : drivers) {
                writer.write(String.join(",", driver));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Show Add Driver dialog
    private void showAddDriverDialog() {
        JTextField driverIdField = new JTextField(10);
        JTextField nameField = new JTextField(10);
        JTextField licenseField = new JTextField(10);
        JTextField phoneField = new JTextField(10);
        JTextField ageField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.setBackground(BACKGROUND_COLOR);
        panel.add(new JLabel("Driver ID:"));
        panel.add(driverIdField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("License Number:"));
        panel.add(licenseField);
        panel.add(new JLabel("Phone Number:"));
        panel.add(phoneField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Add New Driver", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String[] newDriver = {
                    driverIdField.getText(),
                    nameField.getText(),
                    licenseField.getText(),
                    phoneField.getText(),
                    ageField.getText()
            };

            drivers.add(newDriver);
            tableModel.addRow(newDriver);
            saveDrivers(); // Save the updated drivers to file
        }
    }

    // Show Edit Driver dialog
    private void showEditDriverDialog() {
        int selectedRow = driverTable.getSelectedRow();
        if (selectedRow != -1) {
            String driverId = (String) tableModel.getValueAt(selectedRow, 0);
            String name = (String) tableModel.getValueAt(selectedRow, 1);
            String license = (String) tableModel.getValueAt(selectedRow, 2);
            String phone = (String) tableModel.getValueAt(selectedRow, 3);
            String age = (String) tableModel.getValueAt(selectedRow, 4);

            JTextField driverIdField = new JTextField(driverId);
            JTextField nameField = new JTextField(name);
            JTextField licenseField = new JTextField(license);
            JTextField phoneField = new JTextField(phone);
            JTextField ageField = new JTextField(age);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(5, 2));
            panel.setBackground(BACKGROUND_COLOR);
            panel.add(new JLabel("Driver ID:"));
            panel.add(driverIdField);
            panel.add(new JLabel("Name:"));
            panel.add(nameField);
            panel.add(new JLabel("License Number:"));
            panel.add(licenseField);
            panel.add(new JLabel("Phone Number:"));
            panel.add(phoneField);
            panel.add(new JLabel("Age:"));
            panel.add(ageField);

            int option = JOptionPane.showConfirmDialog(this, panel, "Edit Driver", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String[] updatedDriver = {
                        driverIdField.getText(),
                        nameField.getText(),
                        licenseField.getText(),
                        phoneField.getText(),
                        ageField.getText()
                };

                drivers.set(selectedRow, updatedDriver);
                tableModel.setValueAt(driverIdField.getText(), selectedRow, 0);
                tableModel.setValueAt(nameField.getText(), selectedRow, 1);
                tableModel.setValueAt(licenseField.getText(), selectedRow, 2);
                tableModel.setValueAt(phoneField.getText(), selectedRow, 3);
                tableModel.setValueAt(ageField.getText(), selectedRow, 4);

                saveDrivers(); // Save the updated drivers to file
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a driver to edit!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Delete selected driver
    private void deleteDriver() {
        int selectedRow = driverTable.getSelectedRow();
        if (selectedRow != -1) {
            String driverId = (String) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the driver with ID: " + driverId + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                drivers.remove(selectedRow);
                tableModel.removeRow(selectedRow);
                saveDrivers(); // Save the updated drivers to file
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a driver to delete!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ManageDriverFrame());
    }
}
