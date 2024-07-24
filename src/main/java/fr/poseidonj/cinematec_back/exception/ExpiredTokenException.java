package fr.poseidonj.cinematec_back.exception;

public class ExpiredTokenException extends RuntimeException{
    public ExpiredTokenException() {
        super("Given token is expired");
    }
}
