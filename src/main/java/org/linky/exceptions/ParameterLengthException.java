package org.linky.exceptions;

public class ParameterLengthException extends AbstractBaseException {

    public ParameterLengthException(String parameter, LengthErrorType errorType) {
        super(1, String.format("The parameter %s is too %s", parameter, errorType.toString().toLowerCase()));

    }

    public enum LengthErrorType {
        SHORT, LONG
    }
}
