public class CardAccount extends BankAccount {

    public static final double PERCENT_COMMISSION = 1.01; // 1%

    public CardAccount(double sum) {
        super(sum);
    }

    public void subtractSum(double sum) {
        super.subtractSum(sum * PERCENT_COMMISSION);
    }
}
