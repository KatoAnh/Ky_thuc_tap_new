public class SavingsAccount extends BankAccount implements Printable {
    private double interestRate;

    public SavingsAccount(Person owner, double interestRate) {
        super(owner, AccountType.SAVINGS);
        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("So tien rut phai lon hon 0.");
            return;
        }
        if (amount > this.balance) {
            System.out.println("Rut tien that bai. So du khong du.");
        } else {
            this.balance -= amount;
            System.out.println("Rut tien thanh cong. So du moi: " + this.balance);
            this.transactionHistory.add(new Transaction(TransactionType.WITHDRAW, amount));
        }
    }

    @Override
    public void printSummary() {
        System.out.println("=== TOM TAT TAI KHOAN TIET KIEM ===");
        printAccountInfo();
        System.out.println("  Lai suat: " + interestRate + "%/nam");
    }
}