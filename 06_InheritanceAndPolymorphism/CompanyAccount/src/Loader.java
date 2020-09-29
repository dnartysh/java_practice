import Companies.Company;
import Persons.Manager;
import Persons.Operator;
import Persons.Person;
import Persons.TopManager;

public class Loader {

    public static void main(String[] args) {
        Company sberbank = new Company("Sberbank");
        sberbank.hairAll(180, 80, 10);

        sberbank.printList();
    }
}
