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
    public double getSalaryWithPrize() {
        double buf = this.company.MAX_INCOME - this.company.MIN_INCOME;
        double randomIncome = Math.round((Math.random() * buf) + this.company.MIN_INCOME);
        return salaryManager + (randomIncome * this.company.PERCENT_INCOME / 100);
    }

    @Override
    public void setCompany(Company company) {
        this.company = company;
    }
}
