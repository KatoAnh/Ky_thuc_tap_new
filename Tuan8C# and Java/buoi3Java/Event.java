import java.time.LocalDate;

public class Event {
    private static int nextId = 1;
    private String eventId;
    private String eventName;
    private String location;
    private LocalDate date;
    private int maxSeats;

    public Event(String eventName, String location, LocalDate date, int maxSeats) {
        this.eventId = "EVT" + nextId++;
        this.eventName = eventName;
        this.location = location;
        this.date = date;
        this.maxSeats = maxSeats;
    }


    public String getEventId() { return eventId; }
    public String getEventName() { return eventName; }
    public String getLocation() { return location; }
    public LocalDate getDate() { return date; }
    public int getMaxSeats() { return maxSeats; }

    @Override
    public String toString() {
        return "Event ID: " + eventId +
               " | Ten: " + eventName +
               " | Dia diem: " + location +
               " | Ngay: " + date +
               " | So ghe toi da: " + maxSeats;
    }
}