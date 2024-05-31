package jp.co.axa.apidemo.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientCredentialException extends RuntimeException {

    public ClientCredentialException (String value) {
        super(value);
    }
}
