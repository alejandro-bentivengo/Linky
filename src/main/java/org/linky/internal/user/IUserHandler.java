package org.linky.internal.user;

import org.linky.model.internal.acl.User;
import org.linky.internal.IHandler;
import org.linky.model.services.UserRequest;

public interface IUserHandler extends IHandler<UserRequest, User> {
}
