package Accounts;

import java.time.LocalDate;

public class DepositAccount extends BankAccount {

    private final LocalDate currentDate = LocalDate.now();
    private LocalDate datePayment;

    public DepositAccount(double amount) {
        super(amount);
        datePayment = LocalDate.now();
    }

    public boolean addSum(double amount) {
        if (super.addSum(amount)) {
            datePayment = LocalDate.now();
            return true;
        } else {
            return false;
        }
    }

    public boolean subtractSum(double amount) {
        if (currentDate.isAfter(datePayment.plusMonths(1))) {
            if (super.getSum() >= amount) {
                super.subtractSum(amount);
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
