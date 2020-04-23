package org.linky.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

/**
 * Base exception to handle logging
 */
public abstract class AbstractBaseException extends Throwable {

    private static Logger LOG = LoggerFactory.getLogger(AbstractBaseException.class);

    private int responseCode;
    private String errorMessage;

    public AbstractBaseException(int responseCode, Throwable e) {
        this(responseCode, "An unknown error occurred when executing the request.");
        LOG.error("Error encountered when executing request", e);
    }

    public AbstractBaseException(int responseCode, Throwable e, String message) {
        this(responseCode, message);
        LOG.error(message, e);
    }

    public AbstractBaseException(int responseCode, String message) {
        this.responseCode = responseCode;
        this.errorMessage = message;
        LOG.error(message);
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public ResponseEntity buildResponse() {
        return ResponseEntity.status(this.getResponseCode()).header("Content-Type", "text/plain").body(this.getErrorMessage());
    }

}
