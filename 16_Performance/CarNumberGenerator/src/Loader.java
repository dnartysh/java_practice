public class Loader {

    public static void main(String[] args) throws Exception {

        Thread thread1 = new Thread(new MyRun("res/numbers1.txt"));
        Thread thread2 = new Thread(new MyRun("res/numbers2.txt"));

        thread1.start();
        thread2.start();
    }
}