package ru.glushenko.productlist.Exception;

public class ElementNotFound extends RuntimeException {
    public ElementNotFound(String message) {
        super(message);
    }
}
