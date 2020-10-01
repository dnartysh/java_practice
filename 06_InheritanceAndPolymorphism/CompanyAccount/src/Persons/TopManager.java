package Persons;

import Companies.Company;

public class TopManager extends Person {
    protected static double salaryTopManager = 45000;
    private final static double PERCENT_BONUS = 1.5;

    public TopManager(int id) {
        super(id);
    }

    @Override
    public double getSalary() {
        return salaryTopManager;
    }

    @Override
    public double getSalaryWithPrize(Company company) {
        double salary = salaryTopManager;

        if (company.INCOME_COMPANY > 10000000) {
            salary *= PERCENT_BONUS;
        }

        return salary;
    }
}
