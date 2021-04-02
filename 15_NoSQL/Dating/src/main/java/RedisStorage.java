import java.util.ArrayList;
import java.util.Date;
import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

public class RedisStorage {
    private RedissonClient redisson;
    private RKeys rKeys;
    private RScoredSortedSet<Integer> topAccounts;
    private final String HOST = "redis://127.0.0.1:6379";
    private final String KEY = "TOP_ACCOUNTS";

    public void init() {
        Config config = new Config();
        config.useSingleServer().setAddress(HOST);

        try {
            redisson = Redisson.create(config);
        } catch (RedisConnectionException ex) {
            System.out.println("Не удалось подключиться к Redis");
            System.out.println(ex.getMessage());
        }

        rKeys = redisson.getKeys();
        topAccounts = redisson.getScoredSortedSet(KEY);
        rKeys.delete(KEY);
    }

    public void printAccounts() throws InterruptedException {
        ArrayList<Integer> vipList = new ArrayList<>();

        for (Integer value : topAccounts) {
            Thread.sleep(400);
            if (isRandom()) {
                int id = getVipAccountID(value);
                System.out.printf("Пользователь: %d оплатил услугу\nПользователь: %d\n", id, id);
                vipList.add(id);

                if (value != id && vipList.contains(value)) {
                    System.out.printf("Пользователь: %d\n", value);
                }
            } else {
                if (!vipList.contains(value)) {
                    System.out.printf("Пользователь: %d\n", value);
                }
            }
        }
    }

    private boolean isRandom() {
        return Math.random() < 0.2;
    }

    private double getDate() {
        return new Date().getTime();
    }

    public void addAccount(int id) {
        topAccounts.add(getDate(), id);
    }

    public int getVipAccountID(int startIndex) {
        int max = topAccounts.size() - startIndex;
        return (int) (Math.random() * max) + startIndex;
    }
}
