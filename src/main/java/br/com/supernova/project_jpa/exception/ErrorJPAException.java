package br.com.supernova.project_jpa.exception;

public class ErrorJPAException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public ErrorJPAException(String message) {
        throw new RuntimeException("Não foi possível concluir a transação" + message);
    }
}
