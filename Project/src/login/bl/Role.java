package login.bl;

public enum Role {
    ADMIN(0),
    OPERATOR(1),
    CLIENT(2);

    private int value;

    private Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Role fromValue(int value) {
        for (Role role : Role.values()) {
            if (role.value == value) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid Role value: " + value);
    }
}
