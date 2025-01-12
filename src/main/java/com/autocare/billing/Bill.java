import java.util.Date;

public class Bill {

    // Attributs de la classe Bill correspondant aux colonnes de la table 'bill'
    private long id; // Correspond à la colonne 'id'
    private int clientId; // Correspond à la colonne 'client_id'
    private String serviceType; // Correspond à la colonne 'service_type'
    private Date billDate; // Correspond à la colonne 'bill_date'
    private String billStatus; // Correspond à la colonne 'bill_status'
    private double cost; // Correspond à la colonne 'cost'

    // Constructeur par défaut
    public Bill() {
    }

    // Constructeur avec tous les paramètres
    public Bill(long id, int clientId, String serviceType, Date billDate, String billStatus, double cost) {
        this.id = id;
        this.clientId = clientId;
        this.serviceType = serviceType;
        this.billDate = billDate;
        this.billStatus = billStatus;
        this.cost = cost;
    }

    // Getters et Setters pour chaque attribut

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", serviceType='" + serviceType + '\'' +
                ", billDate=" + billDate +
                ", billStatus='" + billStatus + '\'' +
                ", cost=" + cost +
                '}';
    }
}
