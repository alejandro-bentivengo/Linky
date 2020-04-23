package org.linky.exceptions;

import javax.servlet.http.HttpServletResponse;

public class InvalidUrlException extends AbstractBaseException {
    public InvalidUrlException(String url) {
        super(HttpServletResponse.SC_BAD_REQUEST, String.format("The url %s is invalid", url));
    }
}
