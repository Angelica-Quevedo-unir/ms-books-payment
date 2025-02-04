package es.unir.relatosdepapel.payments.service;

import es.unir.relatosdepapel.payments.dto.PaymentRequestDTO;
import es.unir.relatosdepapel.payments.model.Payment;

import java.util.Optional;

public interface IPaymentService {

    /**
     * Processes a new payment request.
     *
     * @param request The payment request details.
     * @return The created payment object.
     */
    Payment processPayment(PaymentRequestDTO request);

    /**
     * Retrieves a payment by its ID.
     *
     * @param paymentId The ID of the payment.
     * @return An Optional containing the payment if found.
     */
    Optional<Payment> getPaymentById(Long paymentId);

    /**
     * Cancels a payment by its ID and restores any related stock.
     *
     * @param paymentId The ID of the payment to cancel.
     */
    void cancelPayment(Long paymentId);
}