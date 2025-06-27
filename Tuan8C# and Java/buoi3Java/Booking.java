import java.time.LocalDateTime;

public class Booking {
    private static long nextId = 1;
    private String bookingId;
    private String userEmail;
    private String eventId;
    private int seatNumber;
    private LocalDateTime bookingTime;

    public Booking(String userEmail, String eventId, int seatNumber) {
        this.bookingId = "BKG" + nextId++;
        this.userEmail = userEmail;
        this.eventId = eventId;
        this.seatNumber = seatNumber;
        this.bookingTime = LocalDateTime.now();
    }

    public String getBookingId() { return bookingId; }
    public String getUserEmail() { return userEmail; }
    public String getEventId() { return eventId; }
    public int getSeatNumber() { return seatNumber; }
    public LocalDateTime getBookingTime() { return bookingTime; }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId +
               " | Email: " + userEmail +
               " | Event ID: " + eventId +
               " | Ghe so: " + seatNumber +
               " | Thoi gian dat: " + bookingTime;
    }
}