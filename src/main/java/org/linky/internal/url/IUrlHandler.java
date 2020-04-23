package org.linky.internal.url;

import org.linky.internal.IHandler;
import org.linky.model.internal.urls.Url;
import org.linky.model.services.UrlRequest;

/**
 * In the future URLs might be handled differently, or could be parsed in some other manner
 * Better be safe and create a plug and play logic from scratch
 */
public interface IUrlHandler extends IHandler<UrlRequest, Url> {
}
