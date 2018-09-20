package se.lunchreader.domain;

public class Meal {
    public final String description;
    public final double priceInSek;

    public Meal(String description, double priceInSek) {
        this.description = description;
        this.priceInSek = priceInSek;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "description='" + description + '\'' +
                ", priceInSek=" + priceInSek +
                '}';
    }
}