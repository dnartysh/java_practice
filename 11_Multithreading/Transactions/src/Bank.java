import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bank {
    private final HashMap<String, Account> accounts = new HashMap<>();
    private final HashMap<String, Account> blackList = new HashMap<>();
    private final Random random = new Random();
    private final long MAX_AMOUNT_TRANSACTION = 50000;
    private final ExecutorService executor = Executors.newFixedThreadPool(50);

    private synchronized boolean isFraud() throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    public void transfer(String fromAccountNum, String toAccountNum, long amount) throws Exception {
        Account from = getAccountFromAccNum(fromAccountNum);
        Account to = getAccountFromAccNum(toAccountNum);

        try {
            if (isAccountCorrect(from, to)) {
                if (from != to && !isAccountInBlackList(from, to) && amount < from.getMoney()) {
                    executor.execute(new TransferThread(from, to, amount));
                } else {
                    System.out.println("Счет заблокирован или недостаточно средств для"
                            + " выполнения операции");
                }

                if (amount > MAX_AMOUNT_TRANSACTION) {
                    if (isFraud()) {
                        addToBlockMap(from, to);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    public long getBalance(String accountNum) throws Exception {
        Account account = getAccountFromAccNum(accountNum);

        if (account == null) {
            throw new Exception("Введенный счет: " + accountNum + " не найден в базе");
        }

        return account.getMoney();
    }

    private void addToBlockMap(Account from, Account to) {
        blackList.put(from.getAccNumber(), from);
        blackList.put(to.getAccNumber(), to);
    }

    private Account getAccountFromAccNum(String accNum) {
        for (Map.Entry<String, Account> item : accounts.entrySet()) {
            if (item.getKey().equals(accNum)) {
                return item.getValue();
            }
        }

        return null;
    }

    private boolean isAccountCorrect(Account from, Account to) {
        if (from == null) {
            System.out.println("Счет отправителя не найден");
            return false;
        } else if (to == null) {
            System.out.println("Счет получателя не найден");
            return false;
        }

        return true;
    }

    private boolean isAccountInBlackList(Account from, Account to) {
        if (blackList.containsValue(from)) {
            return true;
        } else
            return blackList.containsValue(to);
    }

    public void addAccount(String accNum, long initialBalance) {
        accounts.put(accNum, new Account(accNum, initialBalance));
    }

    public HashMap<String, Account> getAccounts() {
        return accounts;
    }
}
