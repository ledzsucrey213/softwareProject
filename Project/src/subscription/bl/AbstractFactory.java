package subscription.bl;


public abstract class AbstractFactory {

    /**
     * Default constructor
     */
    public AbstractFactory() {
    }

    /**
     * This method creates a SubscriptionDAO object. You can modify this
     * method to instantiate different implementations of SubscriptionDAO based
     * on configuration or other criteria.
     * 
     * @return A SubscriptionDAO object
     */
    public abstract SubscriptionDAO createSubscriptionDAO();
}