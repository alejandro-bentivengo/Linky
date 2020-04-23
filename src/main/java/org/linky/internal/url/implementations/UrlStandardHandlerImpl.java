package org.linky.internal.url.implementations;

import org.linky.exceptions.AbstractBaseException;
import org.linky.exceptions.EmptyParameterException;
import org.linky.exceptions.InvalidUrlException;
import org.linky.internal.generators.IGenerator;
import org.linky.internal.url.IUrlHandler;
import org.linky.model.internal.urls.Url;
import org.linky.model.services.UrlRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class UrlStandardHandlerImpl implements IUrlHandler {

    @Autowired
    private IGenerator generator;

    @Override
    public Url handle(UrlRequest request) throws AbstractBaseException {
        Url url = new Url();
        boolean hasHttp = false;
        if (request.getUrl().startsWith("http://")) {
            url.setSecure(false);
            hasHttp = true;
        } else if (request.getUrl().startsWith("https://")) {
            url.setSecure(true);
            hasHttp = true;
        } else if (request.getSecure() == null) {
            throw new EmptyParameterException("secure (otherwise add http or https to the url)");
        }
        if (!hasHttp) {
            request.setUrl(request.getSecure() ? "https://".concat(request.getUrl()) : "http://".concat(request.getUrl()));
        }
        if (!validateUrl(request.getUrl())) {
            throw new InvalidUrlException(request.getUrl());
        }
        url.setSecure(request.getSecure());
        url.setUrl(request.getUrl());
        url.setCode(generator.generate());
        return url;
    }

    private boolean validateUrl(String url) {
        try {
            new URL(url).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
        return true;
    }
}
