package Persons;

import Companies.Company;

public class Operator extends Person implements Employee {

    public Operator(int id) {
        super(id);
    }

    @Override
    public double getSalary() {
        return SALARY_OPERATOR;
    }

    @Override
    public double getPrize(Company company) {
        return 0;
    }
}
