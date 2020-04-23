package org.linky.internal.user.implementations;

import org.linky.exceptions.AbstractBaseException;
import org.linky.exceptions.EmptyParameterException;
import org.linky.internal.user.IUserHandler;
import org.linky.model.internal.acl.User;
import org.linky.exceptions.EmptyBodyException;
import org.linky.exceptions.ParameterLengthException;
import org.linky.model.services.UserRequest;

public class UserStandardHandlerImpl implements IUserHandler {
    @Override
    public User handle(UserRequest value) throws AbstractBaseException {
        validateUser(value);
        return User.builder().username(value.getUsername()).password(value.getPassword()).build();
    }

    /**
     * Ideally this validations should be done using an IValidator interface, creating a validation bean class
     * and making it validate the qualifying properties. For simplicity the method below will suffice.
     */
    private void validateUser(UserRequest userRequest) throws AbstractBaseException {
        if (userRequest != null) {
            if (userRequest.getUsername() == null || userRequest.getPassword() == null) {
                throw new EmptyParameterException("username or password");
            }
            if (userRequest.getUsername().length() < 6) {
                throw new ParameterLengthException("username", ParameterLengthException.LengthErrorType.SHORT);
            }
            if (userRequest.getPassword().length() < 6) {
                throw new ParameterLengthException("password", ParameterLengthException.LengthErrorType.SHORT);
            }
        } else {
            throw new EmptyBodyException();
        }
    }
}
