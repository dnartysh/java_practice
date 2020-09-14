public class BankAccount {
    public double sum;

    public BankAccount(double sum) {
        setSum(sum);
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public void addSum(double sum) {
        this.sum += sum;
    }

    public void subtractSum(double sum) {
        this.sum -= sum;
    }

    public boolean send(BankAccount receiver, double amount) {
        double sumBuffer = getSum();
        double sumReceiverBuffer = receiver.getSum();

        subtractSum(amount);
        receiver.addSum(amount);

        return sumBuffer != getSum() && sumReceiverBuffer != receiver.getSum();
    }
}
