package br.com.jnsdev.task.exception;

/**
 * @Autor Jairo Nascimento
 * @Created 03/06/2024 - 17:51
 */
public class CepNotFoundException extends RuntimeException {
    public CepNotFoundException() {
        super("CEP not found");
    }
}

