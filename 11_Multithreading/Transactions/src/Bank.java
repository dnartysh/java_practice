import java.util.HashMap;
import java.util.Random;

public class Bank {

    private final HashMap<String, Account> accounts = new HashMap<>();
    private final HashMap<String, Account> blackList = new HashMap<>();
    private final Random random = new Random();
    private final long MAX_AMOUNT_TRANSACTION = 50000;


    private synchronized boolean isFraud() throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    public void transfer(String fromAccountNum, String toAccountNum, long amount)
            throws InterruptedException {
        if (fromAccountNum.equals(toAccountNum)) {
            throw new InterruptedException("Невозможно осуществить перевод на один и тот же счёт!");
        }

        Account from = getAccountFromAccNum(fromAccountNum);
        Account to = getAccountFromAccNum(toAccountNum);

        if (from == null || to == null) {
            throw new InterruptedException("Счет: " + (from == null ? fromAccountNum : toAccountNum) +
                    " не найден!");
        }

        boolean fromInBlackList = blackList.containsValue(from);
        boolean toInBlackList = blackList.containsValue(to);

        synchronized (from) {
            synchronized (to) {
                if (fromInBlackList || toInBlackList) {
                    throw new InterruptedException("Счет: " + (fromInBlackList ?
                            fromAccountNum : toAccountNum) + " заблокирован!");
                }

                if (amount > from.getMoney()) {
                    throw new InterruptedException("На счете: " + fromAccountNum +
                            " недостаточно средств для перевода!");
                }

                from.withdraw(amount);
                to.put(amount);

                if (amount > MAX_AMOUNT_TRANSACTION) {
                    if (isFraud()) {
                        addToBlockMap(from, to);
                    }
                }
            }
        }

        System.out.printf("%s - отправка %d руб. С %s на %s. Состояние счета %s"
                        + " - %d руб., состояние счета %s - %d руб.%n",
                Thread.currentThread().getName(), amount, fromAccountNum, toAccountNum,
                fromAccountNum, getBalance(fromAccountNum), toAccountNum, getBalance(toAccountNum));
    }

    private void addToBlockMap(Account from, Account to) {
        blackList.put(from.getAccNumber(), from);
        blackList.put(to.getAccNumber(), to);
    }

    public long getSum() {
        return accounts.values()
                .stream()
                .mapToLong(Account::getMoney)
                .sum();
    }

    private Account getAccountFromAccNum(String accNum) {
        return accounts.get(accNum);
    }

    public Long getBalance(String accountNum) {
        Account account = getAccountFromAccNum(accountNum);

        if (account == null) {
            System.out.println("Введенный счет: " + accountNum + " не найден в базе");
            return null;
        }

        return account.getMoney();
    }


    public void addAccount(String accNum, long initialBalance) {
        accounts.put(accNum, new Account(accNum, initialBalance));
    }
}
