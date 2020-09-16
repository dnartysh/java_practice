package Clients;

public class PhysicalPerson extends Client {

    public void addSum(double amount) {
        setSum(getSum() + amount);
    }

    public void subtractSum(double amount) {
        if (isNumbersEquals(amount, getSum())) {
            setSum(getSum() - amount);
        } else {
            System.out.println("Ошибка операции! Недостаточно средств на счете!");
        }
    }
}
