import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddBookingFrame extends JFrame {

    // Declare the fields
    private JTextField orderNoField;
    private JTextField nameField;
    private JTextField addressField;
    private JTextField phoneField;
    private JTextField destinationField;

    public AddBookingFrame() {
        setTitle("Add New Booking");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Set up GridBagConstraints for layout management
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Spacing between components

        // Order Number Field
        JLabel orderNoLabel = new JLabel("Order Number:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(orderNoLabel, gbc);

        orderNoField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(orderNoField, gbc);

        // Customer Name Field
        JLabel nameLabel = new JLabel("Customer Name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(nameLabel, gbc);

        nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(nameField, gbc);

        // Address Field
        JLabel addressLabel = new JLabel("Address:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(addressLabel, gbc);

        addressField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(addressField, gbc);

        // Phone Field
        JLabel phoneLabel = new JLabel("Phone:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(phoneLabel, gbc);

        phoneField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(phoneField, gbc);

        // Destination Field
        JLabel destinationLabel = new JLabel("Destination:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(destinationLabel, gbc);

        destinationField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(destinationField, gbc);

        // Submit Button
        JButton submitBtn = new JButton("Add Booking");
        submitBtn.setBackground(new Color(0x4CAF50));  // Green color for button
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);
        submitBtn.setPreferredSize(new Dimension(150, 40));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        submitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleBookingSubmission();
            }
        });
        add(submitBtn, gbc);

        setVisible(true);
    }

    // Handle booking submission
    private void handleBookingSubmission() {
        // Get the input values
        String orderNo = orderNoField.getText();
        String name = nameField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        String destination = destinationField.getText();

        // Simple validation to check if fields are empty
        if (orderNo.isEmpty() || name.isEmpty() || address.isEmpty() || phone.isEmpty() || destination.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields mus t be filled!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Append to file (using FileManager utility)
        String bookingDetails = orderNo + "," + name + "," + address + "," + phone + "," + destination;
        FileManager.appendToFile(FileManager.BOOKINGS_FILE, bookingDetails);

        // Show success message
        JOptionPane.showMessageDialog(this, "Booking added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        // Clear the input fields after submission
        orderNoField.setText("");
        nameField.setText("");
        addressField.setText("");
        phoneField.setText("");
        destinationField.setText("");
    }

    public static void main(String[] args) {
        new AddBookingFrame();
    }
}
