package org.linky.exceptions;

import javax.servlet.http.HttpServletResponse;

public class EmptyBodyException extends AbstractBaseException {

    public EmptyBodyException() {
        super(HttpServletResponse.SC_BAD_REQUEST, "Error while executing request, body empty");
    }
}
