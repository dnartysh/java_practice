package Persons;

import Companies.Company;

public class TopManager extends Person {
    private Company company;
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
    public double getSalaryWithPrize() {
        double salary = salaryTopManager;

        if (this.company.INCOME_COMPANY > 10000000) {
            salary *= PERCENT_BONUS;
        }

        return salary;
    }

    @Override
    public void setCompany(Company company) {
        this.company = company;
    }
}
