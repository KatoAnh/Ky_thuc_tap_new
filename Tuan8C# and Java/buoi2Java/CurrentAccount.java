public class CurrentAccount extends BankAccount implements Printable {
    private final double OVERDRAFT_LIMIT = 1000000;

    public CurrentAccount(Person owner) {
        super(owner, AccountType.CURRENT);
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("So tien rut phai lon hon 0.");
            return;
        }
        if (amount > (this.balance + OVERDRAFT_LIMIT)) {
            System.out.println("Rut tien that bai. Vuot qua han muc thau chi.");
        } else {
            this.balance -= amount;
            System.out.println("Rut tien thanh cong. So du moi: " + this.balance);
            this.transactionHistory.add(new Transaction(TransactionType.WITHDRAW, amount));
        }
    }

    @Override
    public void printSummary() {
        System.out.println("=== TOM TAT TAI KHOAN THANH TOAN ===");
        printAccountInfo();
        System.out.println("  Han muc thau chi: " + OVERDRAFT_LIMIT);
    }
}