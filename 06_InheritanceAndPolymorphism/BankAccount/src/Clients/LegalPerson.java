package Clients;

public class LegalPerson extends Client {

    private static final double PERCENT_COMMISSION = 1.01; // 1%

    public void addSum(double amount) {
        deposit(amount, true);
    }

    public void subtractSum(double amount) {
        double sumWithCommission = amount * PERCENT_COMMISSION;

        if (deposit(sumWithCommission, false)) {
            System.out.println("Операция успешно выполнена!");
        } else {
            System.out.println("Ошибка операции! Недостаточно средств на счете!");
        }
    }
}
