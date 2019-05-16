package pl.krzysztof.drzazga.model;

public enum ConferencePath {
    SPRING("Spring"),
    TESTING("Testy"),
    ANDROID("Android");

    private final String name;

    ConferencePath (String s) {
        name = s;
    }

    public String toString() {
        return this.name;
    }
}
