package es.unir.relatosdepapel.payments.common.exception;

public class StockUpdateException extends RuntimeException {
    public StockUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}