public abstract class UserDao {
    private static final String JDBC_URL;
    private static final String DB_USER;
    private static final String DB_PASSWORD;
    // Abstract method to verify login credentials
    public abstract boolean loadUser(String username, String password);
    public abstract User saveUser(String username, String password);
}