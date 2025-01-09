package com.autocare.payment.test;

import com.autocare.payment.PaymentType;
import com.autocare.payment.dao.PaymentTypeDAOMySQL;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTypeTest {

    private PaymentType paymentType;
    private PaymentTypeDAOMySQL paymentTypeDAOMySQL;

    @BeforeEach
    void setUp() {
        paymentType = new PaymentType(1, "Credit Card", 2.5, true);
        paymentTypeDAOMySQL = new PaymentTypeDAOMySQL();
    }

    @Test
    void testPaymentTypeGettersAndSetters() {
        paymentType.setId(2);
        paymentType.setLabel("Debit Card");
        paymentType.setFees(1.5);
        paymentType.setAvailable(false);

        assertEquals(2, paymentType.getId());
        assertEquals("Debit Card", paymentType.getLabel());
        assertEquals(1.5, paymentType.getFees());
        assertFalse(paymentType.isAvailable());
    }

    @Test
    void testInsertPaymentType() {
        assertDoesNotThrow(() -> {
            paymentTypeDAOMySQL.insertPaymentType(paymentType);
        });
    }

    @Test
    void testLoadPaymentTypes() {
        assertDoesNotThrow(() -> {
            List<PaymentType> paymentTypes = paymentTypeDAOMySQL.loadPaymentTypes();
            assertNotNull(paymentTypes);
        });
    }

    @Test
    void testUpdatePaymentType() {
        assertDoesNotThrow(() -> {
            paymentType.setLabel("Updated Label");
            paymentTypeDAOMySQL.updatePaymentType(paymentType);
            // Simulate retrieval after update
            List<PaymentType> paymentTypes = paymentTypeDAOMySQL.loadPaymentTypes();
            assertTrue(paymentTypes.stream().anyMatch(pt -> pt.getLabel().equals("Updated Label")));
        });
    }

    @Test
    void testDeletePaymentType() {
        assertDoesNotThrow(() -> {
            boolean isDeleted = paymentTypeDAOMySQL.deletePaymentType(paymentType.getId());
            assertTrue(isDeleted);
        });
    }

    @Test
    void testPaymentTypeDAOInsertAndRetrieve() throws SQLException {
        paymentTypeDAOMySQL.insertPaymentType(paymentType);
        List<PaymentType> paymentTypes = paymentTypeDAOMySQL.loadPaymentTypes();

        assertNotNull(paymentTypes);
        assertTrue(paymentTypes.stream().anyMatch(pt -> pt.getLabel().equals(paymentType.getLabel())));
    }

    @AfterEach
    void tearDown() {
        try {
            paymentTypeDAOMySQL.deletePaymentType(paymentType.getId());
        } catch (SQLException e) {
            System.err.println("Error during teardown: " + e.getMessage());
        }
    }
}
