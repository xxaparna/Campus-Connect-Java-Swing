import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class CampusConnectGUI {
    public CampusConnectGUI() {
        JFrame frame = new JFrame("Campus Connect - College Event Management");

        frame.setSize(400, 400);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Buttons
        JButton createEventButton = new JButton("Create Event");
        JButton approveEventButton = new JButton("Approve Event");
        JButton viewEventsButton = new JButton("View Events");

        createEventButton.setBounds(100, 100, 200, 30);
        approveEventButton.setBounds(100, 150, 200, 30);
        viewEventsButton.setBounds(100, 200, 200, 30);

        frame.add(createEventButton);
        frame.add(approveEventButton);
        frame.add(viewEventsButton);

        frame.setVisible(true);

        createEventButton.addActionListener(e -> openCreateEventForm());
        approveEventButton.addActionListener(e -> openApproveEventScreen());
        viewEventsButton.addActionListener(e -> openViewEventsScreen());
    }

    // FORM: TO CREATE EVENT
    static void openCreateEventForm() {
        JFrame form = new JFrame("Create New Event");
        form.setSize(400, 400);
        form.setLayout(null);

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setBounds(30, 50, 100, 30);
        JTextField titleField = new JTextField();
        titleField.setBounds(150, 50, 200, 30);

        JLabel dateLabel = new JLabel("Date (yyyy-mm-dd):");
        dateLabel.setBounds(30, 100, 150, 30);
        JTextField dateField = new JTextField();
        dateField.setBounds(150, 100, 200, 30);

        JLabel hallLabel = new JLabel("Hall ID:");
        hallLabel.setBounds(30, 150, 150, 30);
        JTextField hallField = new JTextField();
        hallField.setBounds(150, 150, 200, 30);

        JLabel priorityLabel= new JLabel("Priority Level(1-3):");
        priorityLabel.setBounds(30, 200, 150, 30);
        JTextField priorityField= new JTextField();
        priorityField.setBounds(150, 200, 200, 30);


        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(130, 250, 100, 40);

        submitButton.addActionListener(e -> {
            try {
                String title = titleField.getText();
                String date = dateField.getText();
                int hallId = Integer.parseInt(hallField.getText());
                int priorityLevel=Integer.parseInt(priorityField.getText());



              
                EventService.createEvent(title,date,hallId,priorityLevel);
                JOptionPane.showMessageDialog(form, "Event created Successfully and added to the priority queue!");
                form.dispose();
               
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(form, "Error: " + ex.getMessage());
            }
        });

        form.add(titleLabel);
        form.add(titleField);
        form.add(dateLabel);
        form.add(dateField);
        form.add(hallLabel);
        form.add(hallField);
        form.add(priorityLabel);
        form.add(priorityField);
        form.add(submitButton);

        form.setVisible(true);
    }

    // Screen: Approve Events
    static void openApproveEventScreen() {
        JFrame approveFrame = new JFrame("Approve Events");
        approveFrame.setSize(400, 400);
        approveFrame.setLayout(null);

        List<Event> pendingEvents = EventService.getPendingApprovals();

        if (pendingEvents.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No pending Approvals!");
            return;
        }

        int y = 30;
        for (Event event : pendingEvents) {
            JButton approveButton = new JButton("Approve: " + event.getTitle());
            approveButton.setBounds(50, y, 300, 30);
            approveButton.addActionListener(e -> {
                EventService.resolveConflictsAndApprove(event.getDate(), event.getHallID());
                JOptionPane.showMessageDialog(null,"Top priority event approved. Conflicts rejected.");
                approveFrame.dispose();
                openApproveEventScreen(); 
            });

            approveFrame.add(approveButton);
            y += 50;
        }
        approveFrame.setVisible(true);
    }

    // Screen: View All Events
    static void openViewEventsScreen() {
        JFrame viewFrame = new JFrame("All Events");
        viewFrame.setSize(400, 500);
        viewFrame.setLayout(null);

        List<Event> allEvents;
        try {
            allEvents = EventService.getAllEvents();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error fetching events: " + e.getMessage());
            return;
        }

        if (allEvents.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No Events Created Yet!");
            return;
        }

        int y = 30;
        for (Event event : allEvents) {
            String status;
            if (event.isApproved()) {
                status = "Approved";
            } else if (event.isRejected()) {
                status = "Rejected";
            } else {
                status = "Pending";
            }
            JLabel eventLabel = new JLabel(event.getTitle() + " | " + event.getDate() + " | Hall: " + event.getHallID() + " | " + status);
            eventLabel.setBounds(30, y, 400, 30);
            viewFrame.add(eventLabel);
            y += 40;
        }

        viewFrame.setVisible(true);
    }
    public static void main(String[] args) {
        new LoginForm(); // Launch the login form
    }
}

