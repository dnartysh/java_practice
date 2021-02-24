public class Loader {
    private static int countAccounts = 10;
    private static int countIterations = 100000;

    public static void main(String[] args) {
        Test firstTest = new Test(new Bank(), countAccounts, countIterations);
        firstTest.start();
    }

}
