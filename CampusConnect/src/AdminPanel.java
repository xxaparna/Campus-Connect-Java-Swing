import java.util.List;
import javax.swing.*;

public class AdminPanel {
    public AdminPanel() {
        JFrame frame = new JFrame("Admin Panel");
        frame.setSize(500, 500);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        List<Event> pendingEvents = EventService.getPendingApprovals();

        if (pendingEvents.isEmpty()) {
            // Display "No pending events!" message and buttons
            JLabel noEventsLabel = new JLabel("No pending events!");
            noEventsLabel.setBounds(180, 150, 200, 30);
            frame.add(noEventsLabel);

            JButton backButton = new JButton("Back to Login");
            backButton.setBounds(150, 200, 150, 30);
            backButton.addActionListener(e -> {
                frame.dispose();
                new LoginForm(); // Go back to the login form
            });
            frame.add(backButton);

            JButton exitButton = new JButton("Exit");
            exitButton.setBounds(310, 200, 100, 30);
            exitButton.addActionListener(e -> {
                System.exit(0); // Exit the application
            });
            frame.add(exitButton);

            JButton manageUsersButton = new JButton("Manage Users");
    manageUsersButton.setBounds(180, 250, 150, 30);
    manageUsersButton.addActionListener(e -> {
        frame.dispose();
        new UserManagementPanel(); // Open user management panel
    });
    frame.add(manageUsersButton);



            

            frame.setVisible(true);
            return;
        }

        int y = 30;
        for (Event event : pendingEvents) {
            JLabel eventLabel = new JLabel(event.getTitle() + " | " + event.getDate() + " | Hall: " + event.getHallID());
            eventLabel.setBounds(20, y, 300, 30);

            JButton approveButton = new JButton("Approve");
            approveButton.setBounds(330, y, 100, 30);
            approveButton.addActionListener(e -> {
                EventService.approveEvent(event);
                JOptionPane.showMessageDialog(frame, "Event approved!");
                frame.dispose();
                new AdminPanel(); // Refresh the panel
            });

            JButton rejectButton = new JButton("Reject");
            rejectButton.setBounds(440, y, 100, 30);
            rejectButton.addActionListener(e -> {
                event.reject();
                JOptionPane.showMessageDialog(frame, "Event rejected!");
                frame.dispose();
                new AdminPanel(); // Refresh the panel
            });

            frame.add(eventLabel);
            frame.add(approveButton);
            frame.add(rejectButton);
            y += 50;
        }

        frame.setVisible(true);
    }
}