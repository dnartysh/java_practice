package Clients;

public abstract class Client {

    private double sum;

    public void setSum(double amount) {
        this.sum = amount;
    }

    public double getSum() {
        return sum;
    }

    public void printSum() {
        System.out.println("Остаток на счете - " + sum);
    }

    protected boolean deposit(double amount, boolean plus) {
        if (plus) {
            sum += amount;
            return true;
        } else {
            if (sum >= amount) {
                sum -= amount;
                return true;
            } else {
                return false;
            }
        }
    }

    public abstract void addSum(double amount);
    public abstract void subtractSum(double amount);
}
