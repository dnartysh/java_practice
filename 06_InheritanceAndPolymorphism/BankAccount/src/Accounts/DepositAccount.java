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

    public void subtractSum(double sum) {
        if (currentDate.isAfter(datePayment.plusMonths(1))) {
            super.subtractSum(sum);
        } else {
            System.out.println("Ошибка! Со дня последнего снятия денег еще не прошел 1 месяц");
        }
    }
}
