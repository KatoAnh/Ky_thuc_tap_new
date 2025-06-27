import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManager manager = new StudentManager();

        while (true) {
            System.out.println("\n--- CHUONG TRINH QUAN LY SINH VIEN ---");
            System.out.println("1. Them sinh vien moi");
            System.out.println("2. Hien thi danh sach sinh vien");
            System.out.println("3. Tim sinh vien theo ten");
            System.out.println("4. Tim sinh vien co diem cao nhat");
            System.out.println("5. Sap xep danh sach theo diem giam dan");
            System.out.println("6. Tinh diem trung binh cac sinh vien");
            System.out.println("7. Tinh giai thua cua tuoi sinh vien dau tien");
            System.out.println("0. Thoat chuong trinh");
            System.out.print("Moi ban chon chuc nang: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    manager.addStudent(scanner);
                    break;
                case 2:
                    manager.displayStudents();
                    break;
                case 3:
                    manager.findStudentByName(scanner);
                    break;
                case 4:
                    manager.findStudentWithHighestScore();
                    break;
                case 5:
                    manager.sortByScoreDescending();
                    break;
                case 6:
                    manager.calculateAverageScore();
                    break;
                case 7:
                    manager.calculateFactorialOfFirstStudentAge();
                    break;
                case 0:
                    System.out.println("Cam on da su dung chuong trinh!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Lua chon không hop le. Vui long chon lai.");
            }
        }
    }
}