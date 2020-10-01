package Companies;

import Persons.Person;
import java.util.Comparator;

public class PersonComparatorIncrease extends Company implements Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {
        return Double.compare(o1.getSalaryWithPrize(super.getCompany()), o2.getSalaryWithPrize(super.getCompany()));
    }
}
