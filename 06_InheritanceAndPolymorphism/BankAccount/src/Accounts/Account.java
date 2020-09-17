package Accounts;

public class Account extends BankAccount {

    private boolean noDepositAllowed = false;
    private boolean noWithdrawAllowed = false;
    private final static double COMMISSION_IN_RUB = 17; // размер постоянной комиссии

    public Account(double amount) {
        super(amount);
    }

    private void setDepositAllowed(boolean d) {
        noDepositAllowed = d;
    }

    private void setWithdrawAllowed(boolean w) {
        noWithdrawAllowed = w;
    }

    public boolean addSum(double amount) {
        if (noDepositAllowed) {
            System.out.println("Операция отменена банком! Обратитесь в поддержку.");
            return false;
        } else {
            setDepositAllowed(super.addSum(amount));
            return noDepositAllowed;
        }
    }

    public boolean subtractSum(double amount) {
        if (noWithdrawAllowed) {
            System.out.println("Операция отменена банком! Обратитесь в поддержку.");
            return false;
        } else {
            setWithdrawAllowed(super.subtractSum(getChangedAmount(amount)));
            return noWithdrawAllowed;
        }
    }

    public double getChangedAmount(double amount) {
        if (Math.random() > 0.5) {
            amount += COMMISSION_IN_RUB;
        }
        return amount;
    }
}
