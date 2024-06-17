package com.janiks.forumHub.infra.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String mensagem) {
        super(mensagem);
    }
}
