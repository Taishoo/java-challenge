package jp.co.axa.apidemo.interceptors;

import jp.co.axa.apidemo.exceptions.ClientCredentialException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClientCredentialInterceptor extends HandlerInterceptorAdapter {

    private static final String HTTP_HEADER_CLIENT_ID = "x-client-id";
    private static final String HTTP_HEADER_CLIENT_SECRET = "x-client-secret";

    @Value("${app.client.credential.id}")
    private String clientId;

    @Value("${app.client.credential.secret}")
    private String clientSecret;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /**
         * Main client credential interceptor, validates the headers "x-client-id" and "x-client-secret"
         * if values is not null or invalid
         * else, throw ClientCredentialException
         */

        log.info("Validating incoming request...");
        log.info("Request URL: " + request.getMethod() + ": " + request.getRequestURL());

       // Validate Client ID
       if (request.getHeader(HTTP_HEADER_CLIENT_ID) == null) {
           String message = "Unable to access resource: Client ID is required";
           log.error(message);
           throw new ClientCredentialException(message);
       }

        if (!request.getHeader(HTTP_HEADER_CLIENT_ID).equals(clientId)) {
            String message = "Unable to access resource: Client ID is invalid";
            log.error(message);
            throw new ClientCredentialException(message);
        }
        log.info("Checking Client ID: Success");

        // Validate Client Secret
        if (request.getHeader(HTTP_HEADER_CLIENT_SECRET) == null) {
            String message = "Unable to access resource: Client Secret is required";
            log.error(message);
            throw new ClientCredentialException(message);
        }

        if (!request.getHeader(HTTP_HEADER_CLIENT_SECRET).equals(clientSecret)) {
            String message = "Unable to access resource: Client Secret is invalid";
            log.error(message);
            throw new ClientCredentialException(message);
        }
        log.info("Checking Client Secret: Success");

       log.info("Response: " + response.getStatus());
       return true;
    }
}
