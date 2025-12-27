public class Event implements Comparable<Event> {
    private String title,date;
    private int hallID,priorityLevel; // 1 for admin , 2 for faculty and 3 for student 
    private boolean isApproved;
    private long createdTime;
    public boolean isRejected=false;

    public Event(int id, String title, String date, int hallID,int priorityLevel) {
        this.title = title;
        this.date = date;
        this.hallID = hallID;
        this.priorityLevel=priorityLevel;
        this.isApproved = false;
        this.isRejected = false;
        this.createdTime=System.currentTimeMillis();

    }

    public String getTitle() {
        return title;
    }
    public int getPriorityLevel(){
        return priorityLevel;

    }

    public String getDate() {
        return date;
    }

    public int getHallID() {
        return hallID;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void reject(){
        this.isRejected=true;
    }

    public boolean isRejected(){
        return isRejected;
    }


    public long getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }



    public void approve() {
        this.isApproved = true;
    }

    public void setApproved(boolean approved){
        this.isApproved= approved;
        if(approved){
            this.isRejected=false;
        }
    }

    public void setRejected(boolean rejected){
        this.isRejected=rejected;
        if(rejected){
            this.isApproved=false;
        }
    }

    @Override
    public int compareTo(Event other){
        if(this.priorityLevel!= other.priorityLevel){
            return Integer.compare(this.priorityLevel, other.priorityLevel);  //lower number has higher priority
        }
        else{
            return Long.compare(this.createdTime,other.createdTime);
        }
    }


}