public class Test {
    private Bank bank;
    private int countAccounts;
    private int countIterations;
    private final String ACC_NUM_PATTERN = "1-";

    public Test(Bank bank, int countAccounts, int countIterations) {
        this.bank = bank;
        this.countAccounts = countAccounts;
        this.countIterations = countIterations;
    }

    private void addAccounts() {
        for (int i = 0; i <= countAccounts; i++) {
            long randomAmount = Math.round(100000 * Math.random());
            bank.addAccount(ACC_NUM_PATTERN + i, randomAmount);
        }
    }

    private void getIterations() throws Exception {
        while (countIterations > 0) {
            for (int i = 0; i <= countAccounts; i++) {
                for (int j = 0; j <= countAccounts; j++) {
                    if (i != j) {
                        if (Math.random() < 0.005) {
                            printBalance(i, j);

                            long randomAmount = Math.round(53000 * Math.random());
                            bank.transfer(ACC_NUM_PATTERN + i,
                                    ACC_NUM_PATTERN + j, randomAmount);

                            printBalance(i, j);
                        } else {
                            printBalance(i, j);

                            long randomAmount = Math.round(30 * Math.random());
                            bank.transfer(ACC_NUM_PATTERN + i,
                                    ACC_NUM_PATTERN + j, randomAmount);

                            printBalance(i, j);
                        }
                    }
                }
            }

            countIterations--;
        }
    }

    public void printBalance(int i, int j) throws Exception {
        System.out.println("from: " +
                bank.getBalance(ACC_NUM_PATTERN + i) +
                " | to: " +
                bank.getBalance(ACC_NUM_PATTERN + j));
    }

    public void start() throws Exception {
        addAccounts();
        getIterations();
    }
}













