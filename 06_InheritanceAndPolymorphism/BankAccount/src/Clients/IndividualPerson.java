package Clients;

public class IndividualPerson extends Client {

    private static final double MIN_PERCENT_COMMISSION = 0.995; // 0.5%
    private static final double MAX_PERCENT_COMMISSION = 0.99; // 1%
    private static final double SPLIT_AMOUNT = 1000;

    public void addSum(double amount) {
        if (isNumbersEquals(amount, SPLIT_AMOUNT)) {
            setSum(getSum() + amount * MAX_PERCENT_COMMISSION);
        } else {
            setSum(getSum() + amount * MIN_PERCENT_COMMISSION);
        }
    }

    public void subtractSum(double amount) {
        if (isNumbersEquals(amount, getSum())) { // amount <= super.getSum()
            setSum(getSum() - amount);
        } else {
            System.out.println("Ошибка операции! Недостаточно средств на счете!");
        }
    }
}
