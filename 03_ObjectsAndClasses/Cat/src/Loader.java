import java.util.Scanner;

public class Loader
{
    public static void main(String[] args) {
        printNewCats();
        FeedCat();
        ExplodeCat();
        MeowCat();
        WeightFood();
        System.out.println("Всего кошек: " + Cat.getCount());
    }

    public static void printNewCats() {
        // Murka
        Cat murka = new Cat();
        Cat.count += 1;

        System.out.println("Murka weight before drink: " + murka.getWeight());
        murka.drink(murka.getWeight() / 100);
        System.out.println("Murka weight after drink: " + murka.getWeight());

        // Vasya
        Cat vasya = new Cat();
        Cat.count += 1;

        System.out.println("Vasya weight before feed: " + vasya.getWeight());
        vasya.feed(Math.random() * 100);
        System.out.println("Vasya weight after feed: " + vasya.getWeight());

        // Mater
        Cat mater = new Cat();
        Cat.count += 1;

        System.out.println("Mater: " + mater.getStatus());

        // Charlie
        Cat charlie = new Cat();
        Cat.count += 1;

        System.out.println("Charlie weight: " + charlie.getWeight());

        // Sumrak
        Cat sumrak = new Cat();
        Cat.count += 1;

        System.out.println("Sumrak weight before meow:" + sumrak.getWeight());
        System.out.print("Sumrak: ");
        sumrak.meow();
        System.out.println("Sumrak weight after meow:" + sumrak.getWeight());
    }

    public static void FeedCat() {
        Cat murka = new Cat();
        Cat.count += 1;

        System.out.println("Murka weight before feed: " + murka.getWeight());
        murka.feed(murka.getWeight() / 100);
        System.out.println("Murka weight after feed: " + murka.getWeight());

        Cat vasya = new Cat();
        Cat.count += 1;

        System.out.println("Vasya weight before feed: " + vasya.getWeight());
        vasya.feed(150.0);
        System.out.println("Vasya weight after feed: " + vasya.getWeight());
    }

    public static void ExplodeCat() {
        Cat charlie = new Cat();
        Cat.count += 1;

        while (!charlie.getStatus().equals("Exploded")) {
            charlie.feed(1000.0);
        }

        if (!charlie.getStatus().equals("Dead")) {
            Cat.count -= 1;
        }

        System.out.println("Charlie last weight: " + charlie.getWeight() + ". Charlie is exploded :(");
        System.out.println("Weight food: " + charlie.getSumWeightFood());
    }

    public static void MeowCat() {
        Cat murka = new Cat();
        Cat.count += 1;

        while (!murka.getStatus().equals("Dead")) {
            murka.meow();
        }

        if (!murka.getStatus().equals("Dead")) {
            Cat.count -= 1;
        }

        System.out.println("Murka last weight: " + murka.getWeight() + ". Murka is dead :(");
        System.out.println(Cat.count);
    }

    public static void WeightFood() {
        Cat murka = new Cat();
        for (int i = 0; i < 8; i++){
            murka.feed(Math.random() * 150);
        }

        for (int i = 0; i < 3; i++){
            murka.pee();
        }

        System.out.println("Weight food for Murka: " + murka.getSumWeightFood());
    }
}