import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class UserManagementPanel {
    public UserManagementPanel() {
        JFrame frame = new JFrame("User Management");
        frame.setSize(500, 500);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        List<String> users = loadUsers();
        int y = 30;

        for (String user : users) {
            String[] userDetails = user.split(",");
            String username = userDetails[0];
            String role = userDetails[2];

            JLabel userLabel = new JLabel(username + " | Role: " + role);
            userLabel.setBounds(20, y, 300, 30);

            JButton deleteButton = new JButton("Delete");
            deleteButton.setBounds(330, y, 100, 30);
            deleteButton.addActionListener(e -> {
                users.remove(user);
                saveUsers(users);
                JOptionPane.showMessageDialog(frame, "User deleted!");
                frame.dispose();
                new UserManagementPanel(); // Refresh the panel
            });

            frame.add(userLabel);
            frame.add(deleteButton);
            y += 50;
        }

        JButton backButton = new JButton("Back");
        backButton.setBounds(200, y + 20, 100, 30);
        backButton.addActionListener(e -> {
            frame.dispose();
            new AdminPanel(); // Go back to admin panel
        });
        frame.add(backButton);

        frame.setVisible(true);
    }

    private List<String> loadUsers() {
        List<String> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                users.add(line);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading users!");
        }
        return users;
    }

    private void saveUsers(List<String> users) {
        try (FileWriter writer = new FileWriter("users.txt")) {
            for (String user : users) {
                writer.write(user + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving users!");
        }
    }
}