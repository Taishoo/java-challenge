package jp.co.axa.apidemo.exceptions;


public class ClientCredentialException extends RuntimeException {

    public ClientCredentialException (String value) {
        super(value);
    }
}
