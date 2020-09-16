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

    protected boolean isNumbersEquals(double num1, double num2) {
        return (num2 - num1) > 0.00000001;
    }

    public abstract void addSum(double amount);
    public abstract void subtractSum(double amount);
}
