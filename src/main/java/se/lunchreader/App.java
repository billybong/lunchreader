package se.lunchreader;

import se.lunchreader.restaurants.Mynchen;

import java.io.IOException;

import static java.time.LocalDate.now;

public class App {

    public static void main(String[] args) throws IOException {
        var todaysWeekday = now().getDayOfWeek();
        var mynchenFood = new Mynchen().onMenu(todaysWeekday);
        System.out.println(mynchenFood);
    }
}