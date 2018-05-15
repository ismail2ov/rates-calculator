package es.develex.domain;

public class NotFoundQuoteException extends Exception {

    public NotFoundQuoteException(String message) {
        super(message);
    }
}