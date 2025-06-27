import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankApp {
    private static List<BankAccount> accounts = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- HE THONG QUAN LY NGAN HANG ---");
            System.out.println("1. Tao tai khoan moi");
            System.out.println("2. Gui tien");
            System.out.println("3. Rut tien");
            System.out.println("4. Xem thong tin tai khoan");
            System.out.println("5. Xem lich su giao dich");
            System.out.println("6. Xem danh sach tat ca tai khoan");
            System.out.println("0. Thoat");
            System.out.print("Moi ban chon chuc nang: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    performDeposit();
                    break;
                case 3:
                    performWithdraw();
                    break;
                case 4:
                    viewAccountInfo();
                    break;
                case 5:
                    viewTransactionHistory();
                    break;
                case 6:
                    listAllAccounts();
                    break;
                case 0:
                    System.out.println("Cam on da su dung dich vu!");
                    return;
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        }
    }

    private static void createAccount() {
        System.out.print("Nhap ho ten chu tai khoan: ");
        String name = scanner.nextLine();
        System.out.print("Nhap email: ");
        String email = scanner.nextLine();
        System.out.print("Nhap so dien thoai: ");
        String phone = scanner.nextLine();
        Person owner = new Person(name, email, phone);

        System.out.print("Chon loai tai khoan (1: Tiet kiem, 2: Thanh toan): ");
        int type = Integer.parseInt(scanner.nextLine());
        
        BankAccount newAccount;
        if (type == 1) {
            System.out.print("Nhap lai suat (%/nam): ");
            double rate = Double.parseDouble(scanner.nextLine());
            newAccount = new SavingsAccount(owner, rate);
        } else {
            newAccount = new CurrentAccount(owner);
        }
        
        accounts.add(newAccount);
        System.out.println("Tao tai khoan thanh cong! So tai khoan cua ban la: " + newAccount.getAccountNumber());
    }
    
    private static BankAccount findAccountByNumber() {
        System.out.print("Nhap so tai khoan: ");
        String accNum = scanner.nextLine();
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber().equals(accNum)) {
                return acc;
            }
        }
        System.out.println("Khong tim thay tai khoan.");
        return null;
    }

    private static void performDeposit() {
        BankAccount acc = findAccountByNumber();
        if (acc != null) {
            System.out.print("Nhap so tien can gui: ");
            double amount = Double.parseDouble(scanner.nextLine());
            acc.deposit(amount);
        }
    }

    private static void performWithdraw() {
        BankAccount acc = findAccountByNumber();
        if (acc != null) {
            System.out.print("Nhap so tien can rut: ");
            double amount = Double.parseDouble(scanner.nextLine());
            acc.withdraw(amount);
        }
    }
    
    private static void viewAccountInfo() {
        BankAccount acc = findAccountByNumber();
        if (acc != null) {
            if (acc instanceof Printable) {
                ((Printable) acc).printSummary();
            } else {
                acc.printAccountInfo();
            }
        }
    }

    private static void viewTransactionHistory() {
        BankAccount acc = findAccountByNumber();
        if (acc != null) {
            acc.printTransactionHistory();
        }
    }
    
    private static void listAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("He thong chua co tai khoan nao.");
            return;
        }
        System.out.println("\n--- DANH SACH TAT CA TAI KHOAN ---");
        for (BankAccount acc : accounts) {
            System.out.printf("  - So TK: %s, Chu TK: %s, Loai: %s, So du: %.2f\n",
                    acc.getAccountNumber(), acc.getOwner().getFullName(), acc.accountType, acc.getBalance());
        }
    }
}