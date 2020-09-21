package Accounts;

public class BankAccount {
    private double sum;

    public BankAccount(double amount) {
        setSum(amount);
    }

    public double getSum() {
        return sum;
    }

    protected void setSum(double amount) {
        sum = amount;
    }

    public boolean addSum(double amount) {
        if (amount > 0) {
            sum += amount;
            return true;
        } else {
            return false;
        }
    }

    public boolean subtractSum(double amount) {
        if (sum >= amount) {
            sum -= amount;
            return true;
        } else {
            System.out.println("Недостаточно денег на счете!");
            return false;
        }
    }

    public boolean send(BankAccount receiver, double amount) {
        if (subtractSum(amount)) {
            return receiver.addSum(amount);
        } else {
            return false;
        }
    }
}
