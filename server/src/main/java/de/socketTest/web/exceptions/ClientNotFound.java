package de.socketTest.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Client not Found")
public class ClientNotFound extends RuntimeException {
    // Runtime Exception for the case that a client is not found
}
