import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Mega City Cab - Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(null);
        setLocationRelativeTo(null);

        // Panel for the dashboard with a white background and rounded corners
        JPanel panel = new JPanel();
        panel.setBounds(20, 20, 460, 400);
        panel.setLayout(new GridLayout(5, 1, 20, 20));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new LineBorder(new Color(0xB0B0B0), 2));
        add(panel);

        // Title label for the main menu
        JLabel titleLabel = new JLabel("Mega City Cab Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(0, 20, 500, 30);
        titleLabel.setForeground(new Color(0x333333));
        add(titleLabel);

        // Add New Customer Booking Button
        JButton addBookingBtn = createStyledButton("Add New Customer Booking", "images/book_icon.png");
        addBookingBtn.addActionListener(e -> new AddBookingFrame());
        panel.add(addBookingBtn);

        // Display Booking Details Button
        JButton displayBookingBtn = createStyledButton("Display Booking Details", "images/display_icon.png");
        displayBookingBtn.addActionListener(e -> new DisplayBookingFrame());
        panel.add(displayBookingBtn);

        // Manage Car Information Button
        JButton manageCarBtn = createStyledButton("Manage Car Information", "images/car_icon.png");
        manageCarBtn.addActionListener(e -> new ManageCarFrame());
        panel.add(manageCarBtn);

        // Manage Driver Information Button
        JButton manageDriverBtn = createStyledButton("Manage Driver Information", "images/driver_icon.png");
        manageDriverBtn.addActionListener(e -> new ManageDriverFrame());
        panel.add(manageDriverBtn);

        // Help Button
        JButton helpBtn = createStyledButton("Help", "images/help_icon.png");
        helpBtn.addActionListener(e -> new HelpFrame());
        panel.add(helpBtn);

        // Logout Button
        JButton logoutBtn = createStyledButton("Logout", "images/logout_icon.png");
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });
        panel.add(logoutBtn);

        // Exit Button
        JButton exitBtn = createStyledButton("Exit", "images/exit_icon.png");
        exitBtn.addActionListener(e -> System.exit(0));
        panel.add(exitBtn);

        setVisible(true);
    }

    // Create a styled button with icons and hover effects
    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(0x4CAF50));  // Green color
        button.setForeground(Color.WHITE);
        button.setIcon(new ImageIcon(iconPath));  // Icon for button
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 10, 10, 10));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setPreferredSize(new Dimension(350, 50));

        // Add a hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0x388E3C)); // Darker green
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0x4CAF50)); // Original green
            }
        });

        return button;
    }

    public static void main(String[] args) {
        new MainMenu();
    }
}
