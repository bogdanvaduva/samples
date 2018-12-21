package com.pyknic.servicekit;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response.Status;
import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

/**
 *
 * @author Emil
 */
public class HttpResponseException extends RuntimeException {
    
    private final Status status;
    
    public HttpResponseException(Status status, String message) {
        super(message);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
    
    NanoHTTPD.Response createResponse() {
        return newFixedLengthResponse(status, "text/plain", getMessage());
    }
}