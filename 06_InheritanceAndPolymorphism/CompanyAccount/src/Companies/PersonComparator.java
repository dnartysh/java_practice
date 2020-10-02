package Companies;

import Persons.Person;
import java.util.Comparator;

public class PersonComparator implements Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {
        return Double.compare(o1.getSalaryWithPrize(o1.getCompany()), o2.getSalaryWithPrize(o1.getCompany()));
    }
}
