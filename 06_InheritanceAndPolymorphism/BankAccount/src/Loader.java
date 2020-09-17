import Accounts.Account;
import Accounts.CardAccount;
import Clients.IndividualPerson;

public class Loader {

    public static void main(String[] args) {
//        Account account = new Account(10000);
//        System.out.println("Создание счета account...... " + account.getSum());
//        CardAccount cardAccount = new CardAccount(5000);
//        System.out.println("Создание счета cardAccount...... " + account.getSum());
//
//        account.addSum(400);
//        System.out.println("Внесение наличных account...... " + account.getSum());
//
//        cardAccount.send(account, 1000);
//        System.out.println("Перевод наличных в account...... " + account.getSum());

        IndividualPerson individualPerson = new IndividualPerson();
        individualPerson.addSum(10000);
        System.out.println(individualPerson.getSum());

//        trySendToBlockedAccount();
//        trySendLargeAmount();

    }

    private static void trySendToBlockedAccount() {
        var l1 = new Account(100);
        var l2 = new Account(100);

        boolean l2Put = l2.addSum(10.0);

        boolean l2Tol1Send = l1.send(l2, 10);

        boolean l1Withdraw = l1.subtractSum(10.0);

        assertTrue( l2Put && !l2Tol1Send && l1Withdraw ,
                "l2 уже потратил свои пополнения и send не должен был пройти. " +
                        "А т.к. send не прошел, то и l1 не должен был потратить счетчик снятия");
    }

    private static void trySendLargeAmount() {
        var l1 = new Account(100);
        var l2 = new Account(100);

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
