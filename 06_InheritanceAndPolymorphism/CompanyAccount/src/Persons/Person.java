package Persons;

import Companies.Company;

public abstract class Person {
    private Company company;
    private int id;

    public Person(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract double getSalary();

    public abstract double getSalaryWithPrize(Company company);

    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }
}
