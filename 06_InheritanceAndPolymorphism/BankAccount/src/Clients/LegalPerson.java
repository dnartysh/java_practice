package Clients;

public class LegalPerson extends Client {

    private static final double PERCENT_COMMISSION = 1.01; // 1%

    public void addSum(double amount) {
        setSum(getSum() + amount);
    }

    public void subtractSum(double amount) {
        double sumWithCommission = amount * PERCENT_COMMISSION;

        if (isNumbersEquals(sumWithCommission, getSum())) {
            setSum(getSum() - sumWithCommission);
        } else {
            System.out.println("Ошибка операции! Недостаточно средств на счете!");
        }
    }
}
