package se.lunchreader.domain.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;
import se.lunchreader.domain.data.Meal;

import java.util.List;

@AutoValue
public abstract class RestaurantsResponse {
    @JsonCreator
    static RestaurantsResponse create(@JsonProperty("restaurant") String restaurant, @JsonProperty("meals") List<Meal> meals){
        return new AutoValue_RestaurantsResponse(restaurant, meals);
    }

    @JsonProperty("restaurant")
    abstract String restaurant();
    @JsonProperty("meals")
    abstract List<Meal> meals();
}
