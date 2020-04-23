package org.linky.internal.user.implementations;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.linky.Application;
import org.linky.application.config.InternalConfiguration;
import org.linky.exceptions.AbstractBaseException;
import org.linky.internal.user.IUserHandler;
import org.linky.model.services.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, InternalConfiguration.class})
@ComponentScan("org.linky")
public class UserHandlerTest {

    @Autowired
    private IUserHandler userHandler;

    @Test
    public void TestCorrectUser() throws AbstractBaseException {
        userHandler.handle(UserRequest.builder().username("correctUsername").password("correctPassword").build());
    }

    @Test(expected = AbstractBaseException.class)
    public void TestIncorrectUsername() throws AbstractBaseException {
        userHandler.handle(UserRequest.builder().username("short").password("correctPassword").build());
    }

    @Test(expected = AbstractBaseException.class)
    public void TestIncorrectPassword() throws AbstractBaseException {
        userHandler.handle(UserRequest.builder().username("correctUsername").password("short").build());
    }

    @Test(expected = AbstractBaseException.class)
    public void TestEmptyData() throws AbstractBaseException {
        userHandler.handle(UserRequest.builder().build());
    }

}
