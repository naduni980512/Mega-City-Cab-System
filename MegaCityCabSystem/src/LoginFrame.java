import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Mega City Cab - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(null);
        setLocationRelativeTo(null);

        // Set background color for the panel
        getContentPane().setBackground(new Color(0xF0F0F0));

        // Panel for the login form with rounded corners
        JPanel panel = new JPanel();
        panel.setBounds(50, 50, 300, 270);
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createLineBorder(new Color(0xB0B0B0), 2));
        panel.setOpaque(true);
        add(panel);

        // Title label with a modern font and larger size
        JLabel titleLabel = new JLabel("Welcome to Mega City Cab");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBounds(20, 10, 250, 20);
        titleLabel.setForeground(new Color(0x333333));
        panel.add(titleLabel);

        // Image label
        ImageIcon loginIcon = new ImageIcon("/home/sanuth-newmin/Documents/My projects/MegaCityCabSystem./src/login.png"); // Path to your image

// Scale the image to a smaller size (e.g., 100x100 pixels)
        Image scaledImage = loginIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

// Create a label with the scaled image
        JLabel imageLabel = new JLabel(scaledIcon);
        imageLabel.setBounds(80, 40, scaledIcon.getIconWidth(), scaledIcon.getIconHeight());
        panel.add(imageLabel);


        // Username label and text field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 120, 100, 25);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(120, 120, 150, 25);
        panel.add(usernameField);

        // Password label and password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 160, 100, 25);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 160, 150, 25);
        panel.add(passwordField);

        // Login button with custom style
        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(40, 200, 100, 35);
        loginBtn.setBackground(new Color(0x4CAF50));  // Green color
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        loginBtn.setBorderPainted(false);
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                authenticate();
            }
        });
        panel.add(loginBtn);

        // Exit button with custom style
        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(160, 200, 100, 35);
        exitBtn.setBackground(new Color(0xF44336));  // Red color
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setFont(new Font("Arial", Font.BOLD, 14));
        exitBtn.setBorderPainted(false);
        exitBtn.addActionListener(e -> System.exit(0));
        panel.add(exitBtn);

        // Show the frame
        setVisible(true);
    }

    private void authenticate() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (FileManager.authenticateUser(username, password)) {
            JOptionPane.showMessageDialog(this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new MainMenu(); // Open the main menu upon successful login
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
