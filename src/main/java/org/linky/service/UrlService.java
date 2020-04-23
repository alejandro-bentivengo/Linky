package org.linky.service;

import org.linky.exceptions.AbstractBaseException;
import org.linky.exceptions.EmptyParameterException;
import org.linky.exceptions.NotFoundException;
import org.linky.exceptions.UnauthorizedRequestException;
import org.linky.model.internal.acl.User;
import org.linky.model.internal.urls.Url;
import org.linky.model.services.UrlRequest;
import org.linky.repository.UrlRepository;
import org.linky.repository.UserRepository;
import org.linky.internal.url.IUrlHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * This service layer is used as a binding component to merge the application logic and serve the data
 * to the repository layer
 */
@Service
public class UrlService {

    @Autowired
    private UrlRepository repository;
    @Autowired
    private IUrlHandler urlHandler;
    @Autowired
    private UserRepository userRepository;


    public Url getUrlByCode(String code) throws AbstractBaseException {
        if (code != null && code.length() > 0) {
            return repository.findOne(
                    Example.of(
                            Url.builder()
                                    .code(code)
                                    .build()
                    ))
                    .orElseThrow(
                            () -> new NotFoundException(code)
                    );
        } else {
            throw new EmptyParameterException("code");
        }
    }

    public Url newUrl(@NonNull UrlRequest request) throws AbstractBaseException {
        User user = getUser();
        Url url = urlHandler.handle(request);
        url.setUser(user);
        repository.save(url);
        return url;
    }

    public Url editUrl(String code, @NonNull UrlRequest request) throws AbstractBaseException {
        Url savedUrl = this.getUrlByCode(code);
        getValidatedUserUrl(savedUrl);
        Url handledUrl = urlHandler.handle(request);
        savedUrl.setUrl(handledUrl.getUrl());
        savedUrl.setSecure(handledUrl.getSecure());
        repository.save(savedUrl);
        return savedUrl;
    }

    public void deleteUrl(@NonNull String code) throws AbstractBaseException {
        Url savedUrl = this.getUrlByCode(code);
        getValidatedUserUrl(savedUrl);
        repository.delete(savedUrl);
    }

    public Set<Url> getUserUrls() throws AbstractBaseException {
        User user = getUser();
        return user.getUrls();
    }

    private User getValidatedUserUrl(Url url) throws AbstractBaseException {
        User user = getUser();
        if (!url.getUser().equals(user)) {
            throw new UnauthorizedRequestException();
        }
        return user;
    }

    private User getUser() throws AbstractBaseException {
        User user = userRepository.findOne(
                Example.of(
                        User.builder()
                                .username(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                                .build()
                ))
                .orElseThrow(
                        () -> new NotFoundException("user")
                );
        return user;
    }

}
