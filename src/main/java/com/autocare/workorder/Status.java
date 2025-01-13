

public enum Status {
    NotOrdered,
    InProgress,
    Arrived;

public static Status fromValue(String value) {
    for (Status status : Status.values()) {
        if (status.toString().equals(value)) {
            return status;
        }
    }

    throw new IllegalArgumentException("Invalid Status value: " + value);
}
}
