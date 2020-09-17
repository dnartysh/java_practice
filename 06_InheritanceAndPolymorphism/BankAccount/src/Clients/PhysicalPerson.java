package Clients;

public class PhysicalPerson extends Client {

    public void addSum(double amount) {
        deposit(amount, true);
    }

    public void subtractSum(double amount) {
        if (deposit(amount, false)) {
            System.out.println("Операция успешно выполнена!");
        } else {
            System.out.println("Ошибка операции! Недостаточно средств на счете!");
        }
    }
}
