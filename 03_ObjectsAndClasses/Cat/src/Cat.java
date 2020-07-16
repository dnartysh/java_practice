import java.util.Random;

public class Cat
{
    public static final int COUNT_EYES = 2;
    public static final double MIN_WEIGHT = 1000.0;
    public static final double MAX_WEIGHT = 9000.0;

    public Colors color;
    public static int count;

    private double originWeight;
    private double weight;
    public String catName;

    private double weightFood;

    public Cat() {
        this(1500.0 + 3000.0 * Math.random(), "");
    }

    public Cat(Cat Cat) {
        this(Cat.weight, Cat.catName);
    }

    public Cat(double weight, String catName) {
        if (weight > MAX_WEIGHT) {
            System.out.println("Too much weight");
        }
        else if (weight < MIN_WEIGHT) {
            System.out.println("Too little weight");
        }
        else {
            this.weight = weight;
            this.catName = catName;
            this.originWeight = weight;
            count++;
        }
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
        if(weight < MIN_WEIGHT) {
            count--;
            return "Dead";
        }
        else if(weight > MAX_WEIGHT) {
            count--;
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

    public void setColor(Colors color) {
        this.color = color;
    }

    public Colors getColor() {
        return color;
    }
    
    public void setName(String catName) {
        this.catName = catName;
    }

    public String getName() {
        return catName;
    }
}