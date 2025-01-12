import java.sql.SQLException;
import java.util.List;

public interface BillingDAO {

    /**
     * Insère une nouvelle facture dans la base de données.
     *
     * @param bill l'objet Bill à insérer.
     * @throws SQLException en cas d'erreur avec la base de données.
     */
    void insertBill(Bill bill) throws SQLException;

    /**
     * Charge une facture depuis la base de données à partir de son identifiant.
     *
     * @param billId l'identifiant unique de la facture.
     * @return l'objet Bill correspondant, ou null si non trouvé.
     * @throws SQLException en cas d'erreur avec la base de données.
     */
    Bill loadBill(long billId) throws SQLException;

    /**
     * Supprime une facture de la base de données.
     *
     * @param billId l'identifiant unique de la facture à supprimer.
     * @return true si la suppression a réussi, false sinon.
     * @throws SQLException en cas d'erreur avec la base de données.
     */
    boolean deleteBill(long billId) throws SQLException;

    /**
     * Met à jour une facture existante dans la base de données.
     *
     * @param bill l'objet Bill contenant les nouvelles données.
     * @throws SQLException en cas d'erreur avec la base de données.
     */
    void updateBill(Bill bill) throws SQLException;

    /**
     * Charge toutes les factures de la base de données.
     *
     * @return une liste d'objets Bill.
     * @throws SQLException en cas d'erreur avec la base de données.
     */
    List<Bill> loadAllBills() throws SQLException;
}
