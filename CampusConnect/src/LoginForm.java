import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

public class LoginForm {
    public LoginForm() {
        JFrame frame = new JFrame("Login");
        frame.setSize(300, 200);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(20, 20, 80, 30);
        JTextField userField = new JTextField();
        userField.setBounds(100, 20, 150, 30);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(20, 60, 80, 30);
        JPasswordField passField = new JPasswordField();
        passField.setBounds(100, 60, 150, 30);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 100, 80, 30);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(190, 100, 80, 30);
        signUpButton.addActionListener(e -> {
            frame.dispose();
            new SignUpForm(); // Open the registration form
        });

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            // Validate credentials from users.txt
            if (validateCredentials(username, password)) {
                String role = getUserRole(username);
                frame.dispose();
                if ("admin".equalsIgnoreCase(role)) {
                    new AdminPanel(); // Open Admin Panel
                } else {
                    new UserPanel(); // Open User Panel
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials!");
            }
        });

        frame.add(userLabel);
        frame.add(userField);
        frame.add(passLabel);
        frame.add(passField);
        frame.add(loginButton);
        frame.add(signUpButton);

        frame.setVisible(true);
    }

    private boolean validateCredentials(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading user data!");
        }
        return false;
    }

    private String getUserRole(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[0].equals(username)) {
                    return parts[2]; // Return the role
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading user data!");
        }
        return null;
    }

    public static void main(String[] args) {
        new LoginForm(); // Launch the login form
    }
}