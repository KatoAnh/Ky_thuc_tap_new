import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class StudentManager {
    private List<Student> studentList;
    private static int nextId = 1;

    public StudentManager() {
        this.studentList = new ArrayList<>();
    }

    public void addStudent(Scanner scanner) {
        System.out.print("Nhap ten sinh vien: ");
        String name = scanner.nextLine();

        int age;
        while (true) {
            System.out.print("Nhap tuoi sinh vien: ");
            age = Integer.parseInt(scanner.nextLine());
            if (age > 0) {
                break;
            }
            System.out.println("Tuoi khong hop le, phai lon hon 0. Vui long nhap lai.");
        }

        double score;
        while (true) {
            System.out.print("Nhap diem sinh vien: ");
            score = Double.parseDouble(scanner.nextLine());
            if (score >= 0 && score <= 10) {
                break;
            }
            System.out.println("Diem khong hop le, phai tu 0 den 10. Vui long nhap lai.");
        }
        
        Student newStudent = new Student(nextId++, name, age, score);
        studentList.add(newStudent);
        System.out.println("Them sinh vien thanh cong!");
    }

    public void displayStudents() {
        if (studentList.isEmpty()) {
            System.out.println("Danh sach sinh vien rong.");
            return;
        }
        System.out.println("--- DANH SACH SINH VIEN ---");
        for (Student student : studentList) {
            System.out.println(student);
        }
    }

    public void findStudentByName(Scanner scanner) {
        System.out.print("Nhap ten sinh vien can tim: ");
        String searchTerm = scanner.nextLine().toLowerCase();
        boolean found = false;
        System.out.println("--- KET QUA TIM KIEM ---");
        for (Student student : studentList) {
            if (student.getName().toLowerCase().contains(searchTerm)) {
                System.out.println(student);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Khong tim thay sinh vien nao co ten chua '" + searchTerm + "'.");
        }
    }

    public void findStudentWithHighestScore() {
        if (studentList.isEmpty()) {
            System.out.println("Danh sach sinh vien rong.");
            return;
        }
        Student highestScoreStudent = studentList.get(0);
        for (int i = 1; i < studentList.size(); i++) {
            if (studentList.get(i).getScore() > highestScoreStudent.getScore()) {
                highestScoreStudent = studentList.get(i);
            }
        }
        System.out.println("--- SINH VIEN CO DIEM CAO NHAT ---");
        System.out.println(highestScoreStudent);
    }

    public void sortByScoreDescending() {
        if (studentList.isEmpty()) {
            System.out.println("Danh sach sinh vien rong, khong the sap xep.");
            return;
        }
        studentList.sort((s1, s2) -> Double.compare(s2.getScore(), s1.getScore()));
        System.out.println("Da sap xep danh sach theo diem giam dan.");
        displayStudents();
    }

    public void calculateAverageScore() {
        if (studentList.isEmpty()) {
            System.out.println("Danh sach sinh vien rong.");
            return;
        }
        double totalScore = 0;
        for (Student student : studentList) {
            totalScore += student.getScore();
        }
        double average = totalScore / studentList.size();
        System.out.printf("Diem trung binh cua tat ca sinh vien la: %.2f\n", average);
    }
    
    public void calculateFactorialOfFirstStudentAge() {
        if (studentList.isEmpty()) {
            System.out.println("Danh sach sinh vien rong.");
            return;
        }
        Student firstStudent = studentList.get(0);
        int age = firstStudent.getAge();
        long result = factorial(age);
        System.out.println("Tuoi cua sinh vien dau tien (" + firstStudent.getName() + ") la: " + age);
        System.out.println("Giai thua cua " + age + " la: " + result);
    }

    private long factorial(int n) {
        if (n < 0) {
            return -1;
        }
        if (n == 0 || n == 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }
}