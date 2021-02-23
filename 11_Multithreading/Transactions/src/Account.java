public class Account
{
    private long money;
    private String accNumber;

    public Account(String accNumber, long initialBalance) {
        this.accNumber = accNumber;
        this.money = initialBalance;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }
}
