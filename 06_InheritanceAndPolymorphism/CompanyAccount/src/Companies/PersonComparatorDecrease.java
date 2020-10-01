package Companies;

import Persons.Person;
import java.util.Comparator;

public class PersonComparatorDecrease extends Company implements Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {
        return Double.compare(o2.getSalaryWithPrize(super.getCompany()), o1.getSalaryWithPrize(super.getCompany()));
    }
}
