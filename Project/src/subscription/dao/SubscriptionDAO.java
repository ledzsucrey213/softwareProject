
import java.io.*;
import subscription.bl.*;
import java.util.*;

/**
 * 
 */
public interface SubscriptionDAO {

    /**
     * 
     */
    public void saveSubscription(Subscription subscription);

    /**
     * 
     */
    public List<Subscription> loadSubscription();

    /**
     * 
     */
    public void deleteSubscription(int subscriptionId);

}