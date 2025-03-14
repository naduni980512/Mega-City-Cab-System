import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class CabManagementFrame extends JFrame {

    private JTable cabTable;
    private DefaultTableModel tableModel;
    private ArrayList<String[]> cabs;

    // File to store cab data
    private static final String CABS_FILE = "cabs.csv";

    public CabManagementFrame() {
        setTitle("MegaCity Cab Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize cabs from the file
        cabs = loadCabs();

        // Create table model
        String[] columnNames = {"Cab ID", "Cab Name", "Driver Name", "Capacity", "Location"};
        tableModel = new DefaultTableModel(columnNames, 0);

        // Populate table with cabs data
        for (String[] cab : cabs) {
            tableModel.addRow(cab);
        }

        // JTable for displaying cab data
        cabTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(cabTable);
        add(scrollPane, BorderLayout.CENTER);

        // Button panel for CRUD operations
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addButton = new JButton("Add Cab");
        addButton.addActionListener(e -> showAddCabDialog());

        JButton editButton = new JButton("Edit Cab");
        editButton.addActionListener(e -> showEditCabDialog());

        JButton deleteButton = new JButton("Delete Cab");
        deleteButton.addActionListener(e -> deleteCab());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Load cabs from file (CSV)
    private ArrayList<String[]> loadCabs() {
        ArrayList<String[]> cabList = new ArrayList<>();
        File file = new File(CABS_FILE);

        // Create file if it doesn't exist
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Read cabs from file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                cabList.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cabList;
    }

    // Save cabs to file
    private void saveCabs() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CABS_FILE))) {
            for (String[] cab : cabs) {
                writer.write(String.join(",", cab));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Show Add Cab dialog
    private void showAddCabDialog() {
        JTextField cabIdField = new JTextField(10);
        JTextField cabNameField = new JTextField(10);
        JTextField driverNameField = new JTextField(10);
        JTextField capacityField = new JTextField(10);
        JTextField locationField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(new JLabel("Cab ID:"));
        panel.add(cabIdField);
        panel.add(new JLabel("Cab Name:"));
        panel.add(cabNameField);
        panel.add(new JLabel("Driver Name:"));
        panel.add(driverNameField);
        panel.add(new JLabel("Capacity:"));
        panel.add(capacityField);
        panel.add(new JLabel("Location:"));
        panel.add(locationField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Add New Cab", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String[] newCab = {
                    cabIdField.getText(),
                    cabNameField.getText(),
                    driverNameField.getText(),
                    capacityField.getText(),
                    locationField.getText()
            };
            cabs.add(newCab);
            tableModel.addRow(newCab);
            saveCabs(); // Save updated cabs to file
        }
    }

    // Show Edit Cab dialog
    private void showEditCabDialog() {
        int selectedRow = cabTable.getSelectedRow();
        if (selectedRow != -1) {
            String cabId = (String) tableModel.getValueAt(selectedRow, 0);
            String cabName = (String) tableModel.getValueAt(selectedRow, 1);
            String driverName = (String) tableModel.getValueAt(selectedRow, 2);
            String capacity = (String) tableModel.getValueAt(selectedRow, 3);
            String location = (String) tableModel.getValueAt(selectedRow, 4);

            JTextField cabIdField = new JTextField(cabId);
            JTextField cabNameField = new JTextField(cabName);
            JTextField driverNameField = new JTextField(driverName);
            JTextField capacityField = new JTextField(capacity);
            JTextField locationField = new JTextField(location);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(5, 2));
            panel.add(new JLabel("Cab ID:"));
            panel.add(cabIdField);
            panel.add(new JLabel("Cab Name:"));
            panel.add(cabNameField);
            panel.add(new JLabel("Driver Name:"));
            panel.add(driverNameField);
            panel.add(new JLabel("Capacity:"));
            panel.add(capacityField);
            panel.add(new JLabel("Location:"));
            panel.add(locationField);

            int option = JOptionPane.showConfirmDialog(this, panel, "Edit Cab", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String[] updatedCab = {
                        cabIdField.getText(),
                        cabNameField.getText(),
                        driverNameField.getText(),
                        capacityField.getText(),
                        locationField.getText()
                };

                cabs.set(selectedRow, updatedCab);
                tableModel.setValueAt(cabIdField.getText(), selectedRow, 0);
                tableModel.setValueAt(cabNameField.getText(), selectedRow, 1);
                tableModel.setValueAt(driverNameField.getText(), selectedRow, 2);
                tableModel.setValueAt(capacityField.getText(), selectedRow, 3);
                tableModel.setValueAt(locationField.getText(), selectedRow, 4);

                saveCabs(); // Save updated cabs to file
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a cab to edit.");
        }
    }

    // Delete selected cab
    private void deleteCab() {
        int selectedRow = cabTable.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the selected cab?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                cabs.remove(selectedRow);
                tableModel.removeRow(selectedRow);
                saveCabs(); // Save updated cabs to file
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a cab to delete.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CabManagementFrame());
    }
}
