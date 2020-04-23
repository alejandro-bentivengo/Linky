package org.linky.exceptions;

import javax.servlet.http.HttpServletResponse;

public class UserDuplicatedException extends AbstractBaseException {
    public UserDuplicatedException() {
        super(HttpServletResponse.SC_BAD_REQUEST, "User already registered, please use a different one.");
    }
}
