package Persons;

import Companies.Company;

public class Manager extends Person {
    private Company company;
    protected static double salaryManager = 30000;

    public Manager(int id) {
        super(id);
    }

    @Override
    public double getSalary() {
        return salaryManager;
    }

    @Override
    public double getSalaryWithPrize(Company company) {
        double buf = company.MAX_INCOME - company.MIN_INCOME;
        double randomIncome = Math.round((Math.random() * buf) + company.MIN_INCOME);
        double salary = salaryManager + (randomIncome * company.PERCENT_INCOME / 100);
        return salary;
    }
}
