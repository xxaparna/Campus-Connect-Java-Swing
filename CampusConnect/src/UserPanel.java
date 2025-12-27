import javax.swing.*;
import java.util.List;

public class UserPanel {
    public UserPanel() {
        JFrame frame = new JFrame("User Panel");
        frame.setSize(400, 400); // Increased height to fit all buttons
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Welcome to the User Panel");
        label.setBounds(100, 20, 200, 30);
        frame.add(label);

        JButton createEventButton = new JButton("Create Event");
        createEventButton.setBounds(100, 70, 200, 30);
        createEventButton.addActionListener(e -> CampusConnectGUI.openCreateEventForm());
        frame.add(createEventButton);

        JButton viewEventsButton = new JButton("View My Events");
        viewEventsButton.setBounds(100, 120, 200, 30);
        viewEventsButton.addActionListener(e -> {
            JFrame viewFrame = new JFrame("My Events");
            viewFrame.setSize(400, 500);
            viewFrame.setLayout(null);

            List<Event> allEvents = EventService.getAllEvents(); // Fetch all events
            int y = 30;

            if (allEvents.isEmpty()) {
                JLabel noEventsLabel = new JLabel("No events found!");
                noEventsLabel.setBounds(100, 50, 200, 30);
                viewFrame.add(noEventsLabel);
            } else {
                for (Event event : allEvents) {
                    // Determine the status of the event
                    String status;
                    if (event.isApproved()) {
                        status = "Approved";
                    } else if (event.isRejected()) {
                        status = "Rejected";
                    } else {
                        status = "Pending";
                    }

                    // Display the event details along with its status
                    JLabel eventLabel = new JLabel(event.getTitle() + " | " + event.getDate() + " | Hall: " + event.getHallID() + " | Status: " + status);
                    eventLabel.setBounds(20, y, 350, 30);
                    viewFrame.add(eventLabel);
                    y += 40;
                }
            }

            viewFrame.setVisible(true);
        });
        frame.add(viewEventsButton);

        JButton backButton = new JButton("Back to Login");
        backButton.setBounds(100, 170, 200, 30); // Positioned below the other buttons
        backButton.addActionListener(e -> {
            frame.dispose();
            new LoginForm(); // Go back to the login form
        });
        frame.add(backButton);

        frame.setVisible(true);
    }
}
