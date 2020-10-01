package Persons;

import Companies.Company;

public abstract class Person {
    private int id;

    public Person(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract double getSalary();

    public abstract double getSalaryWithPrize(Company company);
}
