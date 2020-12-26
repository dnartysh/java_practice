package core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class BankStatement {

    private String path;
    private TreeMap<String, TreeSet<Double>> listOfMovements;
    private TreeSet<Double> listAmount;

    public BankStatement(String path) throws IOException {
        this.path = path;
        getAllLinesInFile();
    }

    public void printStatements() {
        double sumIncome = 0;
        double sumExpenses = 0;

        System.out.println("Расходы по компаниям:");

        for (Map.Entry<String, TreeSet<Double>> key : listOfMovements.entrySet()) {
            double sumCompany = 0;

            for (Double amount : key.getValue()) {
                if (amount > 0) {
                    sumIncome += amount;
                } else if (amount < 0) {
                    sumExpenses += amount * -1;
                    sumCompany += amount * -1;
                }
            }

            System.out.println(key.getKey() + ": " + sumCompany);
        }

        System.out.println("\nОбщая сумма расходов: " + sumExpenses);
        System.out.println("Общая сумма доходов: " + sumIncome);
    }

    private void getAllLinesInFile() throws IOException {
        List<String> listWithAllLines = Files.readAllLines(Paths.get(path));
        listOfMovements = new TreeMap<>();

        for (int i = 1; i < listWithAllLines.size(); i++) {
            String[] rows = listWithAllLines.get(i).split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

            if (rows.length > 0) {
                double income = Double.parseDouble(formatDoubleValue(rows[rows.length - 2]));
                double expenses = Double.parseDouble(formatDoubleValue(rows[rows.length - 1]));
                String company = getCompany(rows);

                if (!listOfMovements.containsKey(company)) {
                    listOfMovements.putIfAbsent(company, new TreeSet<>());
                    listOfMovements.get(company).add(income > 0 ? income : expenses);
                } else {
                    TreeSet<Double> amounts = listOfMovements.get(company);
                    amounts.add(getTypeOperation(income, expenses) > 0 ? income : expenses * -1);
                }
            }
        }
    }

    private String formatDoubleValue(String value) {
        return value.replaceAll("([\"])", "").replaceAll("\\,", ".").trim();
    }

    private double getTypeOperation(double income, double expenses) {
        return income - expenses;
    }

    private String getCompany(String[] list) {
        String[] listCompanies = list[list.length - 3].split("((\\\\)|(\\/))|(\\s{2,})");

        return listCompanies[4];
    }
}
