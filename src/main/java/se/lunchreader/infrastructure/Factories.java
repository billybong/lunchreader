package se.lunchreader.infrastructure;

public interface Factories {
    static Factories noStub() {return new Factories() {};}
}
