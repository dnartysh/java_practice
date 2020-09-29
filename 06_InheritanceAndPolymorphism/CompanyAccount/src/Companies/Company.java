package Companies;

import Persons.Manager;
import Persons.Operator;
import Persons.Person;
import Persons.TopManager;
import java.util.HashMap;
import java.util.Map;

public class Company {
    private final static double INCOME_COMPANY = 10000001;
    private final static double MIN_INCOME = 115000;
    private final static double MAX_INCOME = 140000;
    private final static double PERCENT_INCOME = 5;

    private String companyName;
    private HashMap<Integer, Double> listPersons = new HashMap<>();

    public Company(String companyName) {
        this.companyName = companyName;
    }

    public double getMinIncome() {
        return MIN_INCOME;
    }

    public double getMaxIncome() {
        return MAX_INCOME;
    }

    public double getPercentIncome() {
        return PERCENT_INCOME;
    }

    public void hair(Person person) {
        listPersons.put(person.getId(), person.getSalary() + person.getPrize(this));
    }

    public void hairAll(int operatopCount, int managerCount, int topManagerCount) {
        while (operatopCount > 0) {
            Person operator = getNewOperator();
            listPersons.put(operator.getId(), operator.getSalary() + operator.getPrize(this));
            operatopCount--;
        }
        while (managerCount > 0) {
            Person manager = getNewManager();
            listPersons.put(manager.getId(), manager.getSalary() + manager.getPrize(this));
            managerCount--;
        }
        while (topManagerCount > 0) {
            Person topManager = getNewTopManager();
            listPersons.put(topManager.getId(), topManager.getSalary() + topManager.getPrize(this));
            topManagerCount--;
        }
    }

    public void fire(int id) {
        if (id > 0 && id < getLastIdListPerson()) {
            listPersons.remove(id);
        } else {
            System.out.println("Неверно задан id сотрудника!");
        }
    }

    public void printList() {
        for (Map.Entry<Integer, Double> map : listPersons.entrySet()) {
            System.out.println("Сотрудник с id: " + map.getKey() + " - ЗП: " + map.getValue());
        }
    }

    public int getLastIdListPerson() {
        return listPersons.size() + 1;
    }

    public double getIncome() {
        return INCOME_COMPANY;
    }

    private Person getNewManager() {
        return new Manager(getLastIdListPerson());
    }

    private Person getNewOperator() {
        return new Operator(getLastIdListPerson());
    }

    private Person getNewTopManager() {
        return new TopManager(getLastIdListPerson());
    }
}
