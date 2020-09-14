package Accounts;

public class CardAccount extends BankAccount {

    private static final double PERCENT_COMMISSION = 1.01; // 1%

    public CardAccount(double sum) {
        super(sum);
    }

    public void subtractSum(double sum) {
        super.subtractSum(sum * PERCENT_COMMISSION);
    }
}
