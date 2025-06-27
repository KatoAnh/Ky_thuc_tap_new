import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class BankAccount {
    protected String accountNumber;
    protected Person owner;
    protected double balance;
    protected LocalDate createdDate;
    protected AccountType accountType;
    protected List<Transaction> transactionHistory;

    public BankAccount(Person owner, AccountType accountType) {
        this.accountNumber = "ACC" + System.currentTimeMillis();
        this.owner = owner;
        this.balance = 0.0;
        this.createdDate = LocalDate.now();
        this.accountType = accountType;
        this.transactionHistory = new ArrayList<>();
    }
    
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.println("Gui tien thanh cong. So du moi: " + this.balance);
            this.transactionHistory.add(new Transaction(TransactionType.DEPOSIT, amount));
        } else {
            System.out.println("So tien gui vao phai lon hon 0.");
        }
    }

    public abstract void withdraw(double amount);

    public double getBalance() {
        return this.balance;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public Person getOwner() {
        return owner;
    }

    public void printAccountInfo() {
        System.out.println("---------------------------------");
        System.out.println("Thong tin tai khoan:");
        System.out.println("  Chu tai khoan: " + owner.getFullName());
        System.out.println("  So tai khoan: " + accountNumber);
        System.out.println("  Loai tai khoan: " + accountType);
        System.out.println("  So du: " + balance);
        System.out.println("  Ngay tao: " + createdDate);
        System.out.println("---------------------------------");
    }

    public void printTransactionHistory() {
        System.out.println("--- Lich su giao dich cho TK " + accountNumber + " ---");
        if (transactionHistory.isEmpty()) {
            System.out.println("Chua co giao dich nao.");
        } else {
            for (Transaction tx : transactionHistory) {
                System.out.println(tx);
            }
        }
        System.out.println("--------------------------------------");
    }

    public class Transaction {
        private String id;
        private TransactionType type;
        private double amount;
        private LocalDateTime timestamp;

        public Transaction(TransactionType type, double amount) {
            this.id = "TX" + System.currentTimeMillis();
            this.type = type;
            this.amount = amount;
            this.timestamp = LocalDateTime.now();
        }

        @Override
        public String toString() {
            return String.format("  [%s] %s - %s: %.2f",
                    timestamp.toLocalDate(), timestamp.toLocalTime(), type, amount);
        }
    }
}