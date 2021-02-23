public class TransferThread implements Runnable {
    private Account from;
    private Account to;
    private long amount;

    public TransferThread(Account from, Account to, long amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    @Override
    public synchronized void run() {
        long moneyFromBefore = from.getMoney();
        long moneyToBefore = to.getMoney();

        from.setMoney(from.getMoney() - amount);
        to.setMoney(to.getMoney() + amount);

        System.out.println(Thread.currentThread().getName() +
                " | from: " + from.getAccNumber() +
                " | before: " + moneyFromBefore +
                " | after: " + from.getMoney() +
                " | to: " + to.getAccNumber() +
                " | before: " + moneyToBefore +
                " | after: " + to.getMoney());
    }
}
