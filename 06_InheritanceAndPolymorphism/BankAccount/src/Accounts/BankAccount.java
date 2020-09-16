package Accounts;

public class BankAccount {
    private double sum;

    public BankAccount(double sum) {
        setSum(sum);
    }

    public double getSum() {
        return sum;
    }

    private void setSum(double sum) {
        this.sum = sum;
    }

    public void addSum(double sum) {
        this.sum += sum;
    }

    public boolean subtractSum(double sum) {
        if (this.sum >= sum) {
            this.sum -= sum;
            return true;
        } else {
            System.out.println("Недостаточно денег на счете!");
            return false;
        }

    }

    public boolean send(BankAccount receiver, double amount) {
        if (subtractSum(amount)) {
            receiver.addSum(amount);
            return true;
        } else {
            return false;
        }
    }
}
