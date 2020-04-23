package org.linky.internal;

import org.linky.exceptions.AbstractBaseException;

public interface IHandler<T, R> {
    R handle(T value) throws AbstractBaseException;
}
