public class Loader {
    private static int countAccounts = 100000;
    private static int countIterations = 50;

    public static void main(String[] args) throws Exception {
        Test firstTest = new Test(new Bank(), countAccounts, countIterations);
        firstTest.start();
    }

}
