package fr.poseidonj.cinematec_back.exception;

public class IllegalTokenException extends RuntimeException{
    public IllegalTokenException() {
        super("Given invalid token");
    }
}
