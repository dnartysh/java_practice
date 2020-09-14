import java.util.Scanner;

public class Loader {

    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount(100);
        bankAccount.addSum(200);
        System.out.println(bankAccount.getSum());
        bankAccount.subtractSum(100);
        System.out.println(bankAccount.getSum());

        CardAccount cardAccount = new CardAccount(100);
        cardAccount.subtractSum(50);
        System.out.println(cardAccount.getSum());

        DepositAccount depositAccount = new DepositAccount(100);
        depositAccount.subtractSum(10);
        System.out.println(depositAccount.getSum());

        if (bankAccount.send(depositAccount, 50)) {
            System.out.println("Деньги успешно переведены!");
        } else {
            System.out.println("Ошибка перевода!");
        }
    }
}
