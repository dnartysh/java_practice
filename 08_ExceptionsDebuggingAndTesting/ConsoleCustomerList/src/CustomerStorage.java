import java.util.HashMap;
import java.util.regex.Pattern;

public class CustomerStorage {
    private HashMap<String, Customer> storage;

    public CustomerStorage() {
        storage = new HashMap<>();
    }

    public void addCustomer(String data) {
        String[] components = data.split("\\s+");
        if (components.length != 4) {
            throw new RuntimeException("Неверно введен запрос. Пример запроса:\n"
                    + "add Василий Петров vasily.petrov@gmail.com +79215637722");
        } else if (!Pattern.matches(".+@.+\\..+", components[2])) {
            throw new RuntimeException("Неверно введен e-mail.");
        } else if (!Pattern.matches("\\+7.{10}", components[3])) {
            throw new RuntimeException("Неверно введен номер телефона.");
        } else {
            String name = components[0] + " " + components[1];
            storage.put(name, new Customer(name, components[3], components[2]));
        }
    }

    public void listCustomers() {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name) {
        storage.remove(name);
    }

    public int getCount() {
        return storage.size();
    }
}