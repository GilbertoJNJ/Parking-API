package com.gilberto.parkingapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AreaAlreadyRegisteredException extends Exception {
    public AreaAlreadyRegisteredException(String AreaName) {
        super(String.format("Area with name %s already registered in the system.", AreaName));
    }
}
