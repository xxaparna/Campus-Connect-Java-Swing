import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;


public class EventService {
    private static final String FILE_NAME = "events.txt";
    private static Map<String, PriorityQueue<Event>> pendingEvents = new HashMap<>();
    private static List<Event> events = new ArrayList<>();

    private static final String URL="jdbc:mysql://localhost:3306/campusconnect";
    private static final String USER= "root";
    private static final String PASSWORD="aparna22";

    public static void createEvent(String title, String date, int hallId,int priorityLevel) {
        int id = events.size() + 1; // Generate a unique ID based on the size of the events list
        Event event = new Event(id, title, date, hallId, priorityLevel); // Pass the ID as the first argument
        String key = date + "|" + hallId;
        pendingEvents.putIfAbsent(key, new PriorityQueue<>());
        pendingEvents.get(key).add(event);
        events.add(event);
        System.out.println("Event created and added to pending queue");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql ="INSERT INTO events (title,date,hallId,priorityLevel,createdTime)Values(?,?,?,?,?)";
            PreparedStatement stmt=conn.prepareStatement(sql);

            stmt.setString(1,event.getTitle());
            stmt.setString(2,event.getDate());
            stmt.setInt(3,event.getHallID());
            stmt.setInt(4,event.getPriorityLevel());
            stmt.setLong(5,event.getCreatedTime());

            stmt.executeUpdate();
            conn.close();
            System.out.println("Event added to database");
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public static void resolveConflictsAndApprove(String date, int hallId){
        String key=date+ "|"+ hallId;
        PriorityQueue<Event> queue= pendingEvents.get(key);
        if(queue!=null && !queue.isEmpty()){
            Event toApprove=queue.poll();
            toApprove.approve();
            System.out.println("Approved event: "+ toApprove.getTitle());
            while(!queue.isEmpty()){
                Event rejected= queue.poll();
                rejected.reject();
                System.out.println("Rejected event:"+ rejected.getTitle());
               
            }
            pendingEvents.remove(key);
            // saveEvents();
        }else{
            System.out.println("No pending events for this hall/date");
        }
    }




    public static void main(String[] args) {
        System.out.println("EventService is running.");
        String title = "Default Title"; // Replace with actual input or logic
        String date = "2025-01-01";  // Replace with actual input or logic
        int hallId = 1;         // Replace with actual input or logic
        int priorityLevel = 1;  // Replace with actual logic to determine priority level
        int id = events.size() + 1; // Generate a unique ID based on the size of the events list

        Event event = new Event(id, title, date, hallId, priorityLevel); // Pass the ID as the first argument

        String key = date + "|" + hallId;
        pendingEvents.putIfAbsent(key, new PriorityQueue<>());
        pendingEvents.get(key).add(event);
        events.add(event);

        System.out.println("Event added to pending queue");
    }

    // public static void saveEvents(){
    //     try(java.io.PrintWriter writer= new java.io.PrintWriter(new java.io.FileWriter(FILE_NAME))){
    //         for(Event e:events){
    //             writer.println(e.getTitle()+","+e.getDate()+","+e.getHallID()+","+e.isApproved()+","+e.getPriorityLevel()+","+e.getCreatedTime()+","+e.isRejected());
    //         }
    //     }
    //     catch(java.io.IOException e){
    //         System.out.println("Error saving events: "+e.getMessage());
    //     }
    // }

    // public static void loadEvents(){
    //     events.clear();
    //     pendingEvents.clear();
    //     try(java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(FILE_NAME))){
    //         String line;
    //         while ((line = reader.readLine()) != null) {
    //             String[] parts= line.split(",");
    //             if(parts.length>=7){
    //                 String title = parts[0];
    //                 String date= parts[1];
    //                 int hallId= Integer.parseInt(parts[2]);
    //                 boolean approved=Boolean.parseBoolean(parts[3]);
    //                 int priorityLevel=Integer.parseInt(parts[4]);
    //                 Long createdTime=Long.parseLong(parts[5]);
    //                 boolean rejected =Boolean.parseBoolean(parts[6]);

    //                 Event event = new Event(title,date,hallId,priorityLevel);
    //                 event.setCreatedTime(createdTime);
    //                 if(approved)event.approve();
    //                 if(rejected)event.reject();
    //                 events.add(event);

    //                 if(!approved && !rejected){
    //                     String key=date + "|" + hallId;
    //                     pendingEvents.putIfAbsent(key,new PriorityQueue<>());
    //                     pendingEvents.get(key).add(event);
    //                 }
                  
                    
    //             }   }
    //     }
    //     catch(java.io.IOException e){
    //         System.out.println("No saved events found or error reading file.");
    //     }
    // }

    public static List<Event> getAllEvents() {
        return events;
    }

    public static List<Event> getPendingApprovals() {
        List<Event> pending = new ArrayList<>();
        for (Event event : events) {
            if (!event.isApproved() && !event.isRejected()) {
                pending.add(event);
            }  
        }
        return pending;
    }

    public static void approveEvent(Event event) {
        event.approve();
        // saveEvents();
    }

    public static boolean isConflict(String date, int hallId){
        for(Event event: events){
            if(event.getDate().equals(date) && event.getHallID()==hallId){
                return true;
            }
        }
        return false;
    }

    public static void approveNext(String date, int hallId){
        String key= date+ "|"+hallId;

        if(pendingEvents.containsKey(key) && !pendingEvents.get(key).isEmpty()){
            Event event= pendingEvents.get(key).poll();
            event.approve();
            // saveEvents();
            System.out.println("Approved event: "+event.getTitle());
        }
        else{
            System.out.println("No pending events found for this hall and date.");
        }
    }

    public static List<Event> getPendingEvents(String date, int hallId) {
        String key= date+"|"+hallId;
        PriorityQueue<Event> queue= pendingEvents.get(key);
        if(queue!=null){
            return new ArrayList<>(queue);
        }
        else{
            return new ArrayList<>();
        }


    }


}