import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

public class SignUpForm {
    public SignUpForm() {
        JFrame frame = new JFrame("Sign Up");
        frame.setSize(300, 300);
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

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(20, 100, 80, 30);
        String[] roles = {"admin", "faculty", "student"};
        JComboBox<String> roleComboBox = new JComboBox<>(roles);
        roleComboBox.setBounds(100, 100, 150, 30);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(100, 150, 100, 30);
        registerButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            String role = (String) roleComboBox.getSelectedItem();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields!");
                return;
            }

            try {
                File file = new File("users.txt");
                if (!file.exists()) {
                    file.createNewFile(); // Create the file if it doesn't exist
                }

                try (FileWriter writer = new FileWriter(file, true)) {
                    writer.write(username + "," + password + "," + role + "\n");
                    JOptionPane.showMessageDialog(frame, "Registration successful!");
                    frame.dispose();
                    new LoginForm(); // Go back to login form
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error saving user data!");
            }
        });

        frame.add(userLabel);
        frame.add(userField);
        frame.add(passLabel);
        frame.add(passField);
        frame.add(roleLabel);
        frame.add(roleComboBox);
        frame.add(registerButton);

        frame.setVisible(true);
    }
}