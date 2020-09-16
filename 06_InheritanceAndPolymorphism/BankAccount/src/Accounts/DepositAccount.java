package Accounts;

import java.time.LocalDate;

public class DepositAccount extends BankAccount {

    private final LocalDate currentDate = LocalDate.now();
    private LocalDate datePayment;

    public DepositAccount(double sum) {
        super(sum);
        datePayment = LocalDate.now();
    }

    public void addSum(double sum) {
        super.addSum(sum);
        datePayment = LocalDate.now();
    }

    public boolean subtractSum(double sum) {
        if (currentDate.isAfter(datePayment.plusMonths(1))) {
            if (super.getSum() >= sum) {
                super.subtractSum(sum);
                return true;
            } else {
                System.out.println("Недостаточно денег на счете!");
                return false;
            }
        } else {
            System.out.println("Ошибка! Со дня последнего снятия денег еще не прошел 1 месяц");
            return false;
        }
    }
}
