package Persons;

import Companies.Company;

public abstract class Person {
    private int id;
    protected final static double SALARY_OPERATOR = 20000;
    protected final static double SALARY_MANAGER = 30000;
    protected final static double SALARY_TOP_MANAGER = 45000;

    public Person(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract double getSalary();

    public abstract double getPrize(Company company);
}
