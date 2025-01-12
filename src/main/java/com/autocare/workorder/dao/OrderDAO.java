import java.sql.SQLException;

public interface OrderDAO {


    public void insertOrder(Order o) throws SQLException;

    public Order loadOrder(long orderId) throws SQLException;

    public boolean deleteOrder(long orderId) throws SQLException;

    public void updateOrder(Order order) throws SQLException;

    public List<Order> loadAllOrders() throws SQLException;

    
}
