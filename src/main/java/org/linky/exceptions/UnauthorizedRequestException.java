package org.linky.exceptions;

import javax.servlet.http.HttpServletResponse;

public class UnauthorizedRequestException extends AbstractBaseException {
    public UnauthorizedRequestException() {
        super(HttpServletResponse.SC_UNAUTHORIZED, "User not authorized for this operation");
    }
}
