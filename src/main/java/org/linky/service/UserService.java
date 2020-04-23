package org.linky.service;

import org.linky.exceptions.AbstractBaseException;
import org.linky.exceptions.UserDuplicatedException;
import org.linky.internal.user.IUserHandler;
import org.linky.model.services.UserRequest;
import org.linky.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private IUserHandler userHandler;

    public void newUser(UserRequest userRequest) throws AbstractBaseException {
        try {
            repository.save(userHandler.handle(userRequest));
        } catch (DataIntegrityViolationException e) {
            throw new UserDuplicatedException();
        }
    }


}
