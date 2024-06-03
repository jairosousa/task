package br.com.jnsdev.task.model;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

/**
 * @Autor Jairo Nascimento
 * @Created 29/05/2024 - 17:34
 */
public class ErrorResponse {
    private int status;
    private String menssage;

    public ErrorResponse() {
    }

    public ErrorResponse(Builder builder) {
        this.status = builder.status;
        this.menssage = builder.menssage;
    }

    public static ErrorResponse internalError(RuntimeException ex) {
        return ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .build();
    }

    public static ErrorResponse invalidArgumentsError(FieldError fieldError) {
        return ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(fieldError.getDefaultMessage())
                .build();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMenssage() {
        return menssage;
    }

    public void setMenssage(String menssage) {
        this.menssage = menssage;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builderFrom(ErrorResponse response) {
        return new Builder(response);
    }

    public static class Builder {
        private int status;
        private String menssage;

        public Builder() {
        }

        public Builder(ErrorResponse response) {
            this.status = response.getStatus();
            this.menssage = response.getMenssage();
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder message(String message) {
            this.menssage = message;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this);
        }
    }
}
