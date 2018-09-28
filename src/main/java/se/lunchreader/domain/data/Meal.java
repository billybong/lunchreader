package se.lunchreader.domain.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Meal {
    @JsonProperty("desc")
    public final String description;
    @JsonProperty("price")
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