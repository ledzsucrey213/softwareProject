package subscription.bl;

public enum SubscriptionType {
    MONTHLY,
    YEARLY;

    public static SubscriptionType fromString(String upperCase) {
        try {
            return SubscriptionType.valueOf(upperCase.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null; // or handle invalid values accordingly
        }
    }
}
