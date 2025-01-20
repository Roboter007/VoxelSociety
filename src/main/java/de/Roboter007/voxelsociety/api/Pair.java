package de.Roboter007.voxelsociety.api;

public record Pair<A, B>(A a, B b) {

    public static <A, B> Pair<A, B> createPair(A element0, B element1) {
        return new Pair<>(element0, element1);
    }

    public A getFirst() {
        return a;
    }

    public B getSecond() {
        return b;
    }

}
