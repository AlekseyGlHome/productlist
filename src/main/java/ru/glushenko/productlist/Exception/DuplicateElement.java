package ru.glushenko.productlist.Exception;

public class DuplicateElement extends RuntimeException {
    private String message;

    public DuplicateElement(String message) {
        super(message);
    }
}
