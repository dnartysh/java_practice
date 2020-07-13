
public class Cat
{
    public static final int COUNT_EYES = 2;
    public static final int MIN_WEIGHT = 100;
    public static final int MAX_WEIGHT = 1500;

    public static int count;

    private double originWeight;
    private double weight;

    private double minWeight;
    private double maxWeight;
    private double weightFood;

    public Cat() {
        weight = 1500.0 + 3000.0 * Math.random();
        originWeight = weight;
        minWeight = 1000.0;
        maxWeight = 9000.0;
    }

    public Cat(Double weight) {
        this();
        this.weight = weight;
    }

    public void meow() {
        if (!getStatus().equals("Dead")) {
            weight = weight - 1;
            System.out.println("Meow");
        }
        else {
            System.out.println("Cat is dead :(");
        }
    }

    public void feed(Double amount) {
        if (!getStatus().equals("Dead")) {
            weight = weight + amount;
            weightFood += amount;
        }
        else {
            System.out.println("Cat is dead :(");
        }
    }

    public void drink(Double amount) {
        if (!getStatus().equals("Dead")) {
            weight = weight + amount;
        }
        else {
            System.out.println("Cat is dead :(");
        }
    }

    public Double getWeight() {
        return weight;
    }

    public String getStatus() {
        if(weight < minWeight) {
            return "Dead";
        }
        else if(weight > maxWeight) {
            return "Exploded";
        }
        else if(weight > originWeight) {
            return "Sleeping";
        }
        else {
            return "Playing";
        }
    }

    public Double getSumWeightFood() {
        return weightFood;
    }

    public void pee() {
        if (!getStatus().equals("Dead")) {
            weight = weight - Math.random() * 10;
            System.out.println("Pee");
        }
    }

    public static int getCount() {
        return count;
    }
}