
import java.io.*;
import java.util.*;

/**
 * 
 */
public class Order {

    /**
     * Default constructor
     */
    public Order() {
    }

    private long id;
    private String partName;
    private int partQuantity;
    private double price;
    private Status orderStatus;
    public Date estimatedArrival;

    public Order(long id, String partName, int partQuantity, Status status, Date arrival) {
        this.id = id;
        this.partName = partName;
        this.partQuantity = partQuantity;
        this.orderStatus = status;
        this.estimatedArrival = arrival;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPartName() {
        return this.partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public int getPartQuantity() {
        return this.partQuantity;
    }

    public void setPartQuantity(String partQuantity) {
        this.partQuantity = partQuantity;
    }

    public Status getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(Status status) {
        this.orderStatus = status;
    }

    public Date getEstimatedArrival() {
        return this.estimatedArrival;
    }

    public void setEstimatedArrival(Date arrival) {
        this.estimatedArrival = arrival;
    }

    

}