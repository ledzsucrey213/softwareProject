
import java.io.*;
import java.util.*;

/**
 * 
 */
public class SubscriptionService {

    /**
     * Default constructor
     */
    public SubscriptionService() {
    }

    /**
     * @param String user 
     * @param SubscriptionType type 
     * @param Date startDate 
     * @param Double amount 
     * @param Boolean autoRenew 
     * @return
     */
    public Subscription createSubscription(void String user, void SubscriptionType type, void Date startDate, void Double amount, void Boolean autoRenew) {
        // TODO implement here
        return null;
    }

    /**
     * @param String subscriptionId 
     * @return
     */
    public Boolean cancelSubscription(void String subscriptionId) {
        // TODO implement here
        return null;
    }

    /**
     * @param String user 
     * @return
     */
    public List<Subscription> getSubscriptionsByUser(void String user) {
        // TODO implement here
        return null;
    }

    /**
     * @param String subscriptionId 
     * @param Subscription updatedSubscription 
     * @return
     */
    public Boolean updateSubscription(void String subscriptionId, void Subscription updatedSubscription) {
        // TODO implement here
        return null;
    }

    /**
     * @param String subscriptionId 
     * @param Boolean autoRenew 
     * @return
     */
    public Boolean setAutoRenew(void String subscriptionId, void Boolean autoRenew) {
        // TODO implement here
        return null;
    }

}