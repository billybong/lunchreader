package se.lunchreader.domain.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Meal {
    @JsonProperty("desc")
    abstract String description();
    @JsonProperty("price")
    abstract double priceInSek();

    public static Meal create(String description, double priceInSek) {
        return new AutoValue_Meal(description, priceInSek);
    }
}