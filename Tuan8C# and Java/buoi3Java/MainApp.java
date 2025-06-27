import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class MainApp {
    private static EventManager eventManager = new EventManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Them vai su kien mau
        eventManager.addEvent("Hoi thao Java Nang Cao", "Ha Noi", LocalDate.of(2025, 8, 20), 100);
        eventManager.addEvent("Concert Am Nhac Mua Thu", "TP. HCM", LocalDate.of(2025, 9, 15), 500);

        while (true) {
            System.out.println("\n--- HE THONG DAT VE SU KIEN ---");
            System.out.println("1. Hien thi danh sach su kien");
            System.out.println("2. Dat ve");
            System.out.println("3. Xem danh sach ve da dat");
            System.out.println("4. Thong ke ve theo su kien");
            System.out.println("5. Sap xep su kien theo ten");
            System.out.println("0. Thoat");
            System.out.print("Moi ban chon chuc nang: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        eventManager.displayAllEvents();
                        break;
                    case 2:
                        handleBooking();
                        break;
                    case 3:
                        eventManager.displayAllBookings();
                        break;
                    case 4:
                        eventManager.generateBookingStatistics();
                        break;
                    case 5:
                        eventManager.sortEventsByName();
                        break;
                    case 0:
                        System.out.println("Cam on ban da su dung he thong!");
                        return;
                    default:
                        System.out.println("Lua chon khong hop le. Vui long chon lai.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Loi: Vui long nhap mot so.");
            }
        }
    }

    private static void handleBooking() {
        System.out.print("Nhap ID su kien ban muon dat: ");
        String eventId = scanner.nextLine();
        System.out.print("Nhap email cua ban: ");
        String email = scanner.nextLine();
        System.out.print("Chon so ghe: ");
        int seatNumber = Integer.parseInt(scanner.nextLine());

        eventManager.bookTicket(eventId, email, seatNumber);
    }
}