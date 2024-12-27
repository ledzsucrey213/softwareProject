
import java.io.*;
import java.util.*;

/**
 * 
 */
public class Subscription {

    /**
     * Default constructor
     */
	 public Subscription(int id, SubscriptionType type,String label ,boolean isActive, Double amount, String description) {
	        this.id = id;
	        this.type = type;
	        this.is_active = isActive;
	        this.amount = amount;
	        this.description = description;
	        this.label = label;
	    }

	 /**
	  * 
	  */
	 private int id;

	 /**
	  * 
	  */
	 private SubscriptionType type;

	 /**
	  * 
	  */
	 private String label;

	 /**
	  * 
	  */
	 private Boolean is_active;

	 /**
	  * 
	  */
	 private Double amount;

	 /**
	  * 
	  */
	 private String description;

	 // Getter for ID
	 public int getId() {
	     return id;
	 }

	 // Getter for Type
	 public SubscriptionType getType() {
	     return type;
	 }

	 // Getter for Label
	 public String getLabel() {
	     return label;
	 }

	 // Getter for is_active
	 public Boolean isActive() {
	     return is_active;
	 }

	 // Getter for Amount
	 public Double getAmount() {
	     return amount;
	 }

	 // Getter for Description
	 public String getDescription() {
	     return description;
	 }

	 // Optionally fix getSubscriptionById to accept an ID and fetch from DB
	 public Subscription getSubscriptionById(int subscriptionId) {
	     // TODO: Implement database logic here
	     return null;
	 }



    /**
     * @param SubscriptionType type 
     * @return
     */
    public void setType(SubscriptionType type) {
    	    this.type = type;
     }

     public void setIsActive(boolean isActive) {
         this.is_active = isActive; 
     }

     public void setAmount(Double amount) {
         this.amount = amount;
     }
     public void setDescription(String description) {
         this.description = description;
     }

}