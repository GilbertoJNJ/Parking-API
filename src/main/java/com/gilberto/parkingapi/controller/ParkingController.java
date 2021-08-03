package com.gilberto.parkingapi.controller;

import com.gilberto.parkingapi.dto.AreaDTO;
import com.gilberto.parkingapi.exception.AreaAlreadyRegisteredException;
import com.gilberto.parkingapi.exception.AreaNotFoundException;
import com.gilberto.parkingapi.service.ParkingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/Areas")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ParkingController implements ParkingControllerDocs{

    private final ParkingService parkingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AreaDTO createArea(@RequestBody @Valid AreaDTO areaDTO) throws AreaAlreadyRegisteredException {
        return parkingService.createArea(areaDTO);
    }

    @GetMapping("/{name}")
    public AreaDTO findByName(@PathVariable String name) throws AreaNotFoundException {
        return parkingService.findByName(name);
    }

    @GetMapping
    public List<AreaDTO> listAreas() {
        return parkingService.listAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws AreaNotFoundException {
        parkingService.deleteById(id);
    }

    /*@PatchMapping("/{id}/increment")
    public BeerDTO increment(@PathVariable Long id, @RequestBody @Valid QuantityDTO quantityDTO) throws BeerNotFoundException, BeerStockExceededException {
        return beerService.increment(id, quantityDTO.getQuantity());
    }*/
}
