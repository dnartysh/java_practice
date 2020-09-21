import Accounts.LimitedAccount;

public class Loader {

    public static void main(String[] args) {

        if (trySendToBlockedAccount()) {
            System.err.println("Операция не удалась!");
        } else {
            System.out.println("Операция успешно завершена!");
        }

    }

    private static boolean trySendToBlockedAccount() {
        var l1 = new LimitedAccount(100);
        var l2 = new LimitedAccount(100);

        l1.saveState();
        l2.saveState();

        boolean l2Put = l2.addSum(10.0);

        boolean l2Tol1Send = l1.send(l2, 10);

        boolean l1Withdraw = l1.subtractSum(10.0);

        System.out.println(l1.getSum());
        System.out.println(l2.getSum());

        assertTrue( l2Put && !l2Tol1Send && l1Withdraw ,
                "l2 уже потратил свои пополнения и send не должен был пройти. " +
                        "А т.к. send не прошел, то и l1 не должен был потратить счетчик снятия");

        if (!l2Tol1Send) {
            l1.restoreState();
            l2.restoreState();
        }

        System.out.println(l1.getSum());
        System.out.println(l2.getSum());

        return l2Put || l2Tol1Send || l1Withdraw;
    }

    private static void trySendLargeAmount() {
        var l1 = new LimitedAccount(100);
        var l2 = new LimitedAccount(100);

        boolean l1ToL2Send = l1.send(l2, 200);

        boolean l1Withdraw = l1.subtractSum(50.0);
        boolean l2Withdraw = l2.subtractSum(50.0);

        assertTrue( !l1ToL2Send && l1Withdraw && l2Withdraw,
                "первый send не должен был пройти, т.к. мало денег, но последующие " +
                        "снятия должны пройти без проблем, т.к. счета не были тронуты");
    }

    private static void assertTrue(boolean expected, String str) {
        if (expected) {
            System.out.println("✅ УСПЕХ. ");
        } else {
            System.out.println("🔥 Проблема: " + str);
        }
    }
}
