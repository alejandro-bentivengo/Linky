package org.linky.exceptions;

import javax.servlet.http.HttpServletResponse;

public class NotFoundException extends AbstractBaseException {
    public NotFoundException(String value) {
        super(HttpServletResponse.SC_NOT_FOUND, String.format("Searched value %s not found", value));
    }
}
