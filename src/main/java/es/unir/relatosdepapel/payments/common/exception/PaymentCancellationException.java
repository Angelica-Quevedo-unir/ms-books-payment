package es.unir.relatosdepapel.payments.common.exception;

public class PaymentCancellationException extends RuntimeException {
    public PaymentCancellationException(String message) {
        super(message);
    }
}
