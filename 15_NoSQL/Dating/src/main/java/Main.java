public class Main {
    private static final int COUNT_ACCOUNTS = 20;

    public static void main(String[] args) throws InterruptedException {
        int pass = 1;
        RedisStorage redisStorage = new RedisStorage();
        redisStorage.init();

        while (true) {
            for (int i = 1; i <= COUNT_ACCOUNTS; i++) {
                redisStorage.addAccount(i);
            }

            System.out.printf("\nPass #%d\n", pass);
            Thread.sleep(1000);
            redisStorage.printAccounts();
            pass++;
        }
    }
}
