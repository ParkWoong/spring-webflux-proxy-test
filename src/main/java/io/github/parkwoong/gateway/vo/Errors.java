package io.github.parkwoong.gateway.vo;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class Errors extends RuntimeException {
    public static ResponseStatusException INVALID_REQUEST_ERROR =
            new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Request Error");

    public static ResponseStatusException INTERNAL_CLIENT_ERROR =
            new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Internal WebClient Error");
}
