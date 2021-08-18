package my.flick.rd.springproject.model.enums;

public enum SortOption {
    NAME("name"),
    PRICE("price"),
    PUBLICATION_TIME("creationTime");

    String value;

    SortOption(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
