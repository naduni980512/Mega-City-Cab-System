import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class ManageCarFrame extends JFrame {

    private JTable carTable;
    private DefaultTableModel tableModel;
    private ArrayList<String[]> cars;

    // Path to the cars CSV file
    private static final String CARS_FILE = "cars.csv";

    // Color scheme for a professional look
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);  // Steel Blue
    private static final Color BUTTON_COLOR = new Color(30, 144, 255);   // Dodger Blue
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245); // Light Gray
    private static final Color HEADER_COLOR = new Color(60, 60, 60);    // Dark Gray

    public ManageCarFrame() {
        setTitle("Manage Car Information");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize cars by loading from the CSV file
        cars = loadCars();

        // Create table model and set column names
        String[] columnNames = {"Car ID", "Make", "Model", "Year", "Price"};
        tableModel = new DefaultTableModel(columnNames, 0);

        // Fill the table with loaded data
        for (String[] car : cars) {
            tableModel.addRow(car);
        }

        // JTable for displaying cars
        carTable = new JTable(tableModel);
        carTable.setBackground(Color.WHITE);
        carTable.setForeground(Color.BLACK);
        carTable.setFont(new Font("Arial", Font.PLAIN, 14));
        carTable.getTableHeader().setBackground(HEADER_COLOR);
        carTable.getTableHeader().setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(carTable);
        scrollPane.setPreferredSize(new Dimension(580, 300));
        add(scrollPane, BorderLayout.CENTER);

        // Panel for Buttons (CRUD operations)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addBtn = new JButton("Add Car");
        addBtn.setBackground(BUTTON_COLOR);
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);
        addBtn.addActionListener(e -> showAddCarDialog());

        JButton editBtn = new JButton("Edit Car");
        editBtn.setBackground(BUTTON_COLOR);
        editBtn.setForeground(Color.WHITE);
        editBtn.setFocusPainted(false);
        editBtn.addActionListener(e -> showEditCarDialog());

        JButton deleteBtn = new JButton("Delete Car");
        deleteBtn.setBackground(BUTTON_COLOR);
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);
        deleteBtn.addActionListener(e -> deleteCar());

        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set background color for the main frame
        getContentPane().setBackground(BACKGROUND_COLOR);

        setVisible(true);
    }

    // Load cars from file (with file existence check)
    private ArrayList<String[]> loadCars() {
        ArrayList<String[]> carsList = new ArrayList<>();
        File file = new File(CARS_FILE);

        // Check if the file exists, if not create it
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Read cars from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                carsList.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return carsList;
    }

    // Save cars to file
    private void saveCars() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CARS_FILE))) {
            for (String[] car : cars) {
                writer.write(String.join(",", car));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Show Add Car dialog
    private void showAddCarDialog() {
        JTextField carIdField = new JTextField(10);
        JTextField makeField = new JTextField(10);
        JTextField modelField = new JTextField(10);
        JTextField yearField = new JTextField(10);
        JTextField priceField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.setBackground(BACKGROUND_COLOR);
        panel.add(new JLabel("Car ID:"));
        panel.add(carIdField);
        panel.add(new JLabel("Make:"));
        panel.add(makeField);
        panel.add(new JLabel("Model:"));
        panel.add(modelField);
        panel.add(new JLabel("Year:"));
        panel.add(yearField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Add New Car", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String[] newCar = {
                    carIdField.getText(),
                    makeField.getText(),
                    modelField.getText(),
                    yearField.getText(),
                    priceField.getText()
            };

            cars.add(newCar);
            tableModel.addRow(newCar);
            saveCars(); // Save the updated cars to file
        }
    }

    // Show Edit Car dialog
    private void showEditCarDialog() {
        int selectedRow = carTable.getSelectedRow();
        if (selectedRow != -1) {
            String carId = (String) tableModel.getValueAt(selectedRow, 0);
            String make = (String) tableModel.getValueAt(selectedRow, 1);
            String model = (String) tableModel.getValueAt(selectedRow, 2);
            String year = (String) tableModel.getValueAt(selectedRow, 3);
            String price = (String) tableModel.getValueAt(selectedRow, 4);

            JTextField carIdField = new JTextField(carId);
            JTextField makeField = new JTextField(make);
            JTextField modelField = new JTextField(model);
            JTextField yearField = new JTextField(year);
            JTextField priceField = new JTextField(price);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(5, 2));
            panel.setBackground(BACKGROUND_COLOR);
            panel.add(new JLabel("Car ID:"));
            panel.add(carIdField);
            panel.add(new JLabel("Make:"));
            panel.add(makeField);
            panel.add(new JLabel("Model:"));
            panel.add(modelField);
            panel.add(new JLabel("Year:"));
            panel.add(yearField);
            panel.add(new JLabel("Price:"));
            panel.add(priceField);

            int option = JOptionPane.showConfirmDialog(this, panel, "Edit Car", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String[] updatedCar = {
                        carIdField.getText(),
                        makeField.getText(),
                        modelField.getText(),
                        yearField.getText(),
                        priceField.getText()
                };

                cars.set(selectedRow, updatedCar);
                tableModel.setValueAt(carIdField.getText(), selectedRow, 0);
                tableModel.setValueAt(makeField.getText(), selectedRow, 1);
                tableModel.setValueAt(modelField.getText(), selectedRow, 2);
                tableModel.setValueAt(yearField.getText(), selectedRow, 3);
                tableModel.setValueAt(priceField.getText(), selectedRow, 4);

                saveCars(); // Save the updated cars to file
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a car to edit!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Delete selected car
    private void deleteCar() {
        int selectedRow = carTable.getSelectedRow();
        if (selectedRow != -1) {
            String carId = (String) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the car with ID: " + carId + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                cars.remove(selectedRow);
                tableModel.removeRow(selectedRow);
                saveCars(); // Save the updated cars to file
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a car to delete!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ManageCarFrame());
    }
}
