import java.time.LocalDate;

public class DepositAccount extends BankAccount {

    LocalDate currentDate = LocalDate.now();
    LocalDate datePayment;

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
