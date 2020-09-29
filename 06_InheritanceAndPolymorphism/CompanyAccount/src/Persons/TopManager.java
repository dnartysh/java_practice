package Persons;import Companies.Company;

public class TopManager extends Person implements Employee {
    private final static double PERCENT_BONUS = 1.5;

    public TopManager(int id) {
        super(id);
    }

    @Override
    public double getSalary() {
        return SALARY_TOP_MANAGER;
    }

    public double getPrize(Company company) {
        double fixSalary = SALARY_TOP_MANAGER;

        if (company.getIncome() > 10000000) {
            fixSalary *= PERCENT_BONUS;
        }

        return fixSalary;
    }
}
