package Persons;

import Companies.Company;

public class Manager extends Person implements Employee {

    public Manager(int id) {
        super(id);
    }

    @Override
    public double getSalary() {
        return SALARY_MANAGER;
    }

    public double getPrize(Company company) {
        double buf = company.getMaxIncome() - company.getMinIncome();
        double randomIncome = Math.round((Math.random() * buf) + company.getMinIncome());

        return randomIncome * company.getPercentIncome() / 100;
    }

}
