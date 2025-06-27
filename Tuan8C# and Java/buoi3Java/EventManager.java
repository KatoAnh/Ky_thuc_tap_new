import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EventManager {
    private List<Event> events = new ArrayList<>();
    
    private List<Booking> bookings = new LinkedList<>();
    

    private Map<String, Set<Integer>> bookedSeats = new HashMap<>();

    public void addEvent(String name, String location, LocalDate date, int maxSeats) {
        Event newEvent = new Event(name, location, date, maxSeats);
        events.add(newEvent);
        bookedSeats.put(newEvent.getEventId(), new HashSet<>());
        System.out.println("Da them su kien moi thanh cong: " + newEvent.getEventId());
    }

    public void bookTicket(String eventId, String userEmail, int seatNumber) {
        Event event = findEventById(eventId);
        if (event == null) {
            System.out.println("Loi: Khong tim thay su kien voi ID " + eventId);
            return;
        }

        if (seatNumber <= 0 || seatNumber > event.getMaxSeats()) {
            System.out.println("Loi: So ghe khong hop le.");
            return;
        }

        Set<Integer> seatsForEvent = bookedSeats.get(eventId);
        if (seatsForEvent.contains(seatNumber)) {
            System.out.println("Loi: Ghe so " + seatNumber + " da duoc dat. Vui long chon ghe khac.");
            return;
        }

        seatsForEvent.add(seatNumber); 
        Booking newBooking = new Booking(userEmail, eventId, seatNumber);
        bookings.add(newBooking); 
        System.out.println("Dat ve thanh cong! Ma dat ve cua ban la: " + newBooking.getBookingId());
    }

    public void sortEventsByName() {
        events.sort(Comparator.comparing(Event::getEventName));
        System.out.println("Da sap xep danh sach su kien theo ten.");
        displayAllEvents();
    }
    
    public void displayAllEvents() {
        System.out.println("\n--- DANH SACH SU KIEN ---");
        for (Event event : events) {
            System.out.println(event);
        }
    }

    public void displayAllBookings() {
        System.out.println("\n--- DANH SACH VE DA DAT (THEO THOI GIAN) ---");
        Iterator<Booking> iterator = bookings.iterator();
        if (!iterator.hasNext()) {
            System.out.println("Chua co ve nao duoc dat.");
            return;
        }
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    public void generateBookingStatistics() {
        System.out.println("\n--- THONG KE LUOT DAT VE THEO SU KIEN ---");
        Map<String, Integer> eventStats = new HashMap<>();
        for (Booking booking : bookings) {
            String eventId = booking.getEventId();
            eventStats.put(eventId, eventStats.getOrDefault(eventId, 0) + 1);
        }

        if (eventStats.isEmpty()) {
            System.out.println("Chua co ve nao duoc dat.");
            return;
        }

        for (Map.Entry<String, Integer> entry : eventStats.entrySet()) {
            Event event = findEventById(entry.getKey());
            String eventName = (event != null) ? event.getEventName() : "Su kien khong xac dinh";
            System.out.println("  - " + eventName + " (ID: " + entry.getKey() + "): " + entry.getValue() + " ve");
        }
    }

    public Event findEventById(String eventId) {
        for (Event event : events) {
            if (event.getEventId().equalsIgnoreCase(eventId)) {
                return event;
            }
        }
        return null;
    }
}