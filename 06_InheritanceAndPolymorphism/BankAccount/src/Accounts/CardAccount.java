package Accounts;

public class CardAccount extends BankAccount {

    private static final double PERCENT_COMMISSION = 1.01; // 1%

    public CardAccount(double sum) {
        super(sum);
    }

    public boolean subtractSum(double sum) {
        if (super.getSum() >= sum) {
            super.subtractSum(sum * PERCENT_COMMISSION);
            return true;
        } else {
            System.out.println("Недостаточно денег на счете!");
            return false;
        }
    }
}
