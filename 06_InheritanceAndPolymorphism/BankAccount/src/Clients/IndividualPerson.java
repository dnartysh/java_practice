package Clients;

public class IndividualPerson extends Client {

    private static final double MIN_PERCENT_COMMISSION = 0.995; // 0.5%
    private static final double MAX_PERCENT_COMMISSION = 0.99; // 1%
    private static final double SPLIT_AMOUNT = 1000;

    public void addSum(double amount) {
        if (amount < SPLIT_AMOUNT) {
            deposit(amount * MAX_PERCENT_COMMISSION, true);
        } else {
            deposit(amount * MIN_PERCENT_COMMISSION, true);
        }
    }

    public void subtractSum(double amount) {
        if (deposit(amount, false)) {
            System.out.println("Операция успешно выполнена!");
        } else {
            System.out.println("Ошибка операции! Недостаточно средств на счете!");
        }
    }
}
