package Companies;

import Persons.Person;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Company {

    public final double INCOME_COMPANY = 10000001;
    public final double MIN_INCOME = 115000;
    public final double MAX_INCOME = 140000;
    public final double PERCENT_INCOME = 5;

    private final List<Person> listPersons = new ArrayList<>();

    public void hair(Person person) {
        listPersons.add(person);
    }

    public void hairAll(List<Person> persons) {
        listPersons.addAll(persons);
    }

    public void fire(int id) {
        if (id > 0 && id < listPersons.size()) {
            listPersons.remove(id - 1);
        } else {
            System.out.println("Введен некорректный id сотрудника!");
        }

    }

    public void printList() {
        for (Person person : listPersons) {
            System.out
                    .println("Сотрудник с id: " + person.getId() + " получает ЗП(с учетом премии): "
                            + person.getSalaryWithPrize(this));
        }
    }

    public int getLastIdListPerson() {
        return listPersons.size() + 1;
    }

    public void sortListPersonsIncrease() {
        listPersons.sort(new PersonComparatorIncrease());
    }

    public void sortListPersonsDecrease() {
        listPersons.sort(new PersonComparatorDecrease());
    }

    public Company getCompany() {
        return this;
    }

    public List<Person> getTopSalaryStaff(int count) {
        if (count > 0 && count < listPersons.size()) {
            List<Person> sortedList = new ArrayList<>();
            sortListPersonsDecrease();

            for (int i = 0; i < count; i++) {
                sortedList.add(listPersons.get(i));
            }

            return sortedList;
        } else {
            System.out.println("Введено некорректное число!");

            return null;
        }
    }

    public List<Person> getLowestSalaryStaff(int count) {
        if (count > 0 && count < listPersons.size()) {
            List<Person> sortedList = new ArrayList<>();
            sortListPersonsIncrease();

            for (int i = 0; i < count; i++) {
                sortedList.add(listPersons.get(i));
            }

            return sortedList;
        } else {
            System.out.println("Введено некорректное число!");

            return null;
        }
    }

}
