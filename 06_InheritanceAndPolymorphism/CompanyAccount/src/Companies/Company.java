package Companies;

import Persons.Person;
import java.util.ArrayList;
import java.util.List;

public class Company {
    public final double INCOME_COMPANY = 10000001;
    public final double MIN_INCOME = 115000;
    public final double MAX_INCOME = 140000;
    public final double PERCENT_INCOME = 5;

    private final List<Person> listPersons = new ArrayList<>();

    public void hair(Person person) {
        person.setCompany(this);
        listPersons.add(person);
    }

    public void hairAll(List<Person> persons) {
        for (Person person : persons) {
            person.setCompany(this);
        }

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
                            + person.getSalaryWithPrize());
        }
    }

    public int getLastIdListPerson() {
        return listPersons.size() + 1;
    }

    public List<Person> getTopSalaryStaff(int count) {
        if (count > 0 && count < listPersons.size()) {
            listPersons.sort(new PersonComparator().reversed());
            return listPersons.subList(0, count);
        } else {
            System.out.println("Введено некорректное число!");
            return null;
        }
    }

    public List<Person> getLowestSalaryStaff(int count) {
        if (count > 0 && count < listPersons.size()) {
            listPersons.sort(new PersonComparator());
            return listPersons.subList(0, count);
        } else {
            System.out.println("Введено некорректное число!");
            return null;
        }
    }

}
