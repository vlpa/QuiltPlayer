package com.quiltplayer.external.covers.exception;

/**
 * If a request limit exists, this needs to be checked and no requests should be done if this
 * exception get thrown.
 * 
 * @author Vlado Palczynski
 * 
 */
public class RequestOverFlowException extends Exception {
    private static final long serialVersionUID = 1307464635890274404L;

    public RequestOverFlowException() {
        super("Max requests reached");
    }
}
