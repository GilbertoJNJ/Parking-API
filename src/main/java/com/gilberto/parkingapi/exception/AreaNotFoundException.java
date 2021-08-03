package com.gilberto.parkingapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AreaNotFoundException extends Exception {
    public AreaNotFoundException(String AreaName) {
        super(String.format("Area with name %s not found in the system.", AreaName));
    }

    public AreaNotFoundException(Long id) {
        super(String.format("Area with id %s not found in the system.", id));
    }
}
