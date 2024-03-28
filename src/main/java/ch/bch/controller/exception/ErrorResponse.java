package ch.bch.controller.exception;

import lombok.Builder;

@Builder
public class ErrorResponse {

    Integer error;

    String errorMessage;

    String errorDescription;

    public Integer getError() {
        return error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
