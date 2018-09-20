package se.lunchreader;

import se.lunchreader.restaurants.Mynchen;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        var mynchenFood = new Mynchen().onMenu();
        System.out.println(mynchenFood);
    }
}