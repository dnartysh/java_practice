import java.util.Scanner;

public class Loader
{
    public static void main(String[] args) {
        while (true) {
            System.out.println("1 - Create new cats\n2 - Feed the cats\n3 - Blow up a cat\n" +
                    "4 - Get meow cat\nEnter method number: ");

            Scanner scanner = new Scanner(System.in);
            int chooseNumber = scanner.nextInt();

            switch (chooseNumber) {
                case 1: {
                    getNewCats();
                    break;
                }
                case 2: {
                    getFeed();
                    break;
                }
                case 3: {
                    getExplode();
                    break;
                }
                case 4: {
                    getMeowCat();
                    break;
                }
            }
        }
    }

    public static void getNewCats() {
        // Murka
        Cat murka = new Cat();

        System.out.println("Murka weight before drink: " + murka.getWeight());
        murka.drink(murka.getWeight() / 100);
        System.out.println("Murka weight after drink: " + murka.getWeight());

        // Vasya
        Cat vasya = new Cat();

        System.out.println("Vasya weight before feed: " + vasya.getWeight());
        vasya.feed(Math.random() * 100);
        System.out.println("Vasya weight after feed: " + vasya.getWeight());

        // Mater
        Cat mater = new Cat();

        System.out.println("Mater: " + mater.getStatus());

        // Charlie
        Cat charlie = new Cat();

        System.out.println("Charlie weight: " + charlie.getWeight());

        // Sumrak
        Cat sumrak = new Cat();

        System.out.println("Sumrak weight before meow:" + sumrak.getWeight());
        System.out.print("Sumrak: ");
        sumrak.meow();
        System.out.println("Sumrak weight after meow:" + sumrak.getWeight());
    }

    public static void getFeed() {
        Cat murka = new Cat();

        System.out.println("Murka weight before feed: " + murka.getWeight());
        murka.feed(murka.getWeight() / 100);
        System.out.println("Murka weight after feed: " + murka.getWeight());

        Cat vasya = new Cat();

        System.out.println("Vasya weight before feed: " + vasya.getWeight());
        vasya.feed(150.0);
        System.out.println("Vasya weight after feed: " + vasya.getWeight());
    }

    public static void getExplode() {
        Cat charlie = new Cat();

        while (charlie.getStatus() != "Exploded") {
            charlie.feed(1000.0);
        }

        System.out.println("Charlie last weight: " + charlie.getWeight() + ". Charlie is exploded :(");
    }

    public static void getMeowCat() {
        Cat murka = new Cat();

        while (murka.getStatus() != "Dead") {
            murka.meow();
        }

        System.out.println("Murka last weight: " + murka.getWeight() + ". Murka is dead :(");
    }

}