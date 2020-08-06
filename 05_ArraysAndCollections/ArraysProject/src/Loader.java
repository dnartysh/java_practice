
public class Loader {
    private static final int COUNT_PATIENTS = 30;
    private static final float MIN_TEMP_PATIENTS = 32;
    private static final float MAX_TEMP_PATIENTS = 40;
    private static final float MIN_TEMP_HEALTHY_PATIENTS = 36.2f;
    private static final float MAX_TEMP_HEALTHY_PATIENTS = 36.9f;

    public static void main(String[] args) {
        orderArray();
        float[] listTemperatures = getArrayTemperatures();
        printConditionPatients(listTemperatures);
        printAverageTemperatures(listTemperatures);
        printCountHealthyPatients(listTemperatures);
        printXArray();
    }

    private static void orderArray() {
        String text = "Каждый охотник желает знать, где сидит фазан";
        String[] textArray = text.split(",?\\s+");

        String buffer;

        for (int i = 0; i < textArray.length / 2; i++) {
            buffer = textArray[textArray.length - i - 1];
            textArray[textArray.length - i - 1] = textArray[i];
            textArray[i] = buffer;
        }

        for (String textWord : textArray) {
            System.out.println(textWord);
        }
    }

    private static float[] getArrayTemperatures() {
        float[] listTemperatures = new float[COUNT_PATIENTS];

        for (int i = 0; i < listTemperatures.length; i++) {
            listTemperatures[i] = (float) Math.round(((Math.random() * (MAX_TEMP_PATIENTS - MIN_TEMP_PATIENTS)) + MIN_TEMP_PATIENTS) * 10) / 10;
        }

        return listTemperatures;
    }

    private static void printConditionPatients(float[] listTemperatures) {
        System.out.print("Температуры пациентов: ");

        for (float listI : listTemperatures) {
            System.out.print(listI + " ");
        }
    }

    private static void printAverageTemperatures(float[] listTemperatures) {
        float sumTemp = 0.0f;
        for (float tempFloat : listTemperatures) {
            sumTemp += tempFloat;
        }

        System.out.print("\nСредняя температура: ");
        System.out.println((float) Math.round((sumTemp / listTemperatures.length) * 100) / 100);
    }

    private static void printCountHealthyPatients(float[] listTemperatures) {
        int countHealthyPatients = 0;

        for (float numberTemp : listTemperatures) {
            if (numberTemp > MIN_TEMP_HEALTHY_PATIENTS && numberTemp < MAX_TEMP_HEALTHY_PATIENTS) {
                countHealthyPatients++;
            }
        }

        System.out.println("Количество здоровых пациентов: " + countHealthyPatients);
    }

    private static void printXArray() {
        String[][] array = new String[7][7];

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (i == j || j == array[i].length - i - 1) {
                    array[i][j] = "X";
                } else {
                    array[i][j] = " ";
                }
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
    }
}
