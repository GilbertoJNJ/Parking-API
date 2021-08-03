package com.gilberto.parkingapi.controller;

import com.gilberto.parkingapi.dto.AreaDTO;
import com.gilberto.parkingapi.exception.AreaAlreadyRegisteredException;
import com.gilberto.parkingapi.exception.AreaNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Api("Manages parking")
public interface ParkingControllerDocs {

    @ApiOperation(value = "Area creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success Area creation"),
            @ApiResponse(code = 400, message = "Missing required fields or wrong field range value.")
    })
    AreaDTO createArea(AreaDTO areaDTO) throws AreaAlreadyRegisteredException;

    @ApiOperation(value = "Returns area found by a given name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success area found in the system"),
            @ApiResponse(code = 404, message = "Area with given name not found.")
    })
    AreaDTO findByName(@PathVariable String name) throws AreaNotFoundException;

    @ApiOperation(value = "Returns a list of all Areas registered in the system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of all Areas registered in the system"),
    })
    List<AreaDTO> listAreas();

    @ApiOperation(value = "Delete a area found by a given valid Id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success area deleted in the system"),
            @ApiResponse(code = 404, message = "Area with given id not found.")
    })
    void deleteById(@PathVariable Long id) throws AreaNotFoundException;
}

