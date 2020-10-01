import Companies.Company;
import Persons.Manager;
import Persons.Operator;
import Persons.Person;
import Persons.TopManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Loader {

    public static void main(String[] args) {
        Company sberbank = new Company();

//        sberbank.hair(new Operator(sberbank.getLastIdListPerson()));
//        sberbank.hair(new Manager(sberbank.getLastIdListPerson()));
//        sberbank.hair(new TopManager(sberbank.getLastIdListPerson()));
        sberbank.hairAll(getListPersons(sberbank));

        printResults(sberbank, sberbank.getTopSalaryStaff(15), "Самые высокие ЗП:");
        printResults(sberbank, sberbank.getLowestSalaryStaff(30), "Самые низкие ЗП:");

        firePersons(sberbank, sberbank.getLastIdListPerson() / 2);

        printResults(sberbank, sberbank.getTopSalaryStaff(15), "Самые высокие ЗП:");
        printResults(sberbank, sberbank.getLowestSalaryStaff(30), "Самые низкие ЗП:");
    }

    public static List<Person> getListPersons(Company company) {
        List<Person> personList = new ArrayList<>();
        int lastIdPerson = company.getLastIdListPerson();

        for (int i = 0; i < 180; i++) {
            personList.add(new Operator(lastIdPerson));
            lastIdPerson++;
        }
        for (int i = 0; i < 80; i++) {
            personList.add(new Manager(lastIdPerson));
            lastIdPerson++;
        }
        for (int i = 0; i < 10; i++) {
            personList.add(new TopManager(lastIdPerson));
            lastIdPerson++;
        }

        return personList;
    }

    public static void firePersons(Company company, int count) {
        for (int i = 1; i <= count; i++) {
            company.fire(i);
        }
    }

    public static void printResults(Company company, List<Person> list, String message) {
        System.out.println(message);

        for (Person person : list) {
            System.out.println(person.getSalaryWithPrize(company));
        }
    }
}
