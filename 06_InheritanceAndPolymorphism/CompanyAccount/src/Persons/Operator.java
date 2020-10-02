package Persons;

import Companies.Company;

public class Operator extends Person {
    private Company company;
    protected static double salaryOperator = 20000;

    public Operator(int id) {
        super(id);
    }

    @Override
    public double getSalary() {
        return salaryOperator;
    }

    @Override
    public double getSalaryWithPrize(Company company) {
        return salaryOperator;
    }
}
