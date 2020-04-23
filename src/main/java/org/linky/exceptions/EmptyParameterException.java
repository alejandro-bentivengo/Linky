package org.linky.exceptions;

import javax.servlet.http.HttpServletResponse;

public class EmptyParameterException extends AbstractBaseException {
    public EmptyParameterException(String parameter) {
        super(HttpServletResponse.SC_BAD_REQUEST, String.format("The parameter %s is empty or missing", parameter));
    }
}
