package se.lunchreader.domain.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;
import se.lunchreader.domain.data.Meal;

import java.util.List;

@AutoValue
abstract class RestaurantsResponse {
    static RestaurantsResponse create(String restaurant, List<Meal> meals){
        return new AutoValue_RestaurantsResponse(restaurant, meals);
    }

    @JsonProperty("restaurant")
    abstract String restaurant();
    @JsonProperty("meals")
    abstract List<Meal> meals();
}
