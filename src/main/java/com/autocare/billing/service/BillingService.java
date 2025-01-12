package com.autocare.billing.service;

import com.autocare.billing.Bill;
import com.autocare.billing.dao.BillingDAO;
import com.autocare.billing.factory.BillingAbstractFactory;

import java.sql.SQLException;
import java.util.List;

public class BillingService {

    final private BillingDAO billingDAO;

    /**
     * Constructeur pour initialiser le DAO à partir de la factory.
     *
     * @param billingFactory une instance de BillingAbstractFactory pour créer le DAO.
     */
    public BillingService(BillingAbstractFactory billingFactory) {
        this.billingDAO = billingFactory.createBillingDAO();
    }

    /**
     * Récupère toutes les factures depuis la base de données.
     *
     * @return une liste d'objets Bill.
     * @throws SQLException en cas d'erreur avec la base de données.
     */
    public List<Bill> getAllBills() throws SQLException {
        return billingDAO.loadAllBills();
    }

    /**
     * Ajoute une nouvelle facture dans la base de données.
     *
     * @param bill l'objet Bill à insérer.
     * @throws SQLException en cas d'erreur avec la base de données.
     */
    public void generateBill(Bill bill) throws SQLException {
        billingDAO.insertBill(bill);
    }

    /**
     * Supprime une facture existante de la base de données.
     *
     * @param bill l'objet Bill à supprimer.
     * @throws SQLException en cas d'erreur avec la base de données.
     */
    public void deleteBill(Bill bill) throws SQLException {
        billingDAO.deleteBill(bill.getId());
    }

    /**
     * Met à jour une facture existante dans la base de données.
     *
     * @param bill l'objet Bill contenant les nouvelles données.
     * @throws SQLException en cas d'erreur avec la base de données.
     */
    public void updateBill(Bill bill) throws SQLException {
        billingDAO.updateBill(bill);
    }
}
