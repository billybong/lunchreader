package se.lunchreader.domain.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Meal {
    @JsonProperty("desc")
    abstract String description();
    @JsonProperty("price")
    abstract double priceInSek();

    @JsonCreator
    public static Meal create(@JsonProperty("desc") String description, @JsonProperty("price") double priceInSek) {
        return new AutoValue_Meal(description, priceInSek);
    }
}