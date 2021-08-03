package com.gilberto.parkingapi.service;

import com.gilberto.parkingapi.dto.AreaDTO;
import com.gilberto.parkingapi.entity.Area;
import com.gilberto.parkingapi.exception.AreaAlreadyRegisteredException;
import com.gilberto.parkingapi.exception.AreaNotFoundException;
import com.gilberto.parkingapi.mapper.AreaMapper;
import com.gilberto.parkingapi.repository.ParkingRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ParkingService {

    private final ParkingRepository parkingRepository;

    private final AreaMapper areaMapper = AreaMapper.INSTANCE;

    public AreaDTO createArea(AreaDTO areaDTO) throws AreaAlreadyRegisteredException {
        verifyIfIsAlreadyRegistered(areaDTO.getName());
        Area area = areaMapper.toModel(areaDTO);
        Area savedArea = parkingRepository.save(area);
        return areaMapper.toDTO(savedArea);
    }

    public AreaDTO findByName(String name) throws AreaNotFoundException {
        Area foundArea = parkingRepository.findByName(name)
                .orElseThrow(() -> new AreaNotFoundException(name));
        return areaMapper.toDTO(foundArea);
    }

    public List<AreaDTO> listAll() {
        return parkingRepository.findAll()
                .stream()
                .map(areaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) throws AreaNotFoundException {
        verifyIfExists(id);
        parkingRepository.deleteById(id);
    }

    private void verifyIfIsAlreadyRegistered(String name) throws AreaAlreadyRegisteredException {
        Optional<Area> optSavedArea = parkingRepository.findByName(name);
        if (optSavedArea.isPresent()) {
            throw new AreaAlreadyRegisteredException(name);
        }
    }

    private Area verifyIfExists(Long id) throws AreaNotFoundException {
        return parkingRepository.findById(id)
                .orElseThrow(() -> new AreaNotFoundException(id));
    }

    /*public BeerDTO increment(Long id, int quantityToIncrement) throws BeerNotFoundException, BeerStockExceededException {
        Beer beerToIncrementStock = verifyIfExists(id);
        int quantityAfterIncrement = quantityToIncrement + beerToIncrementStock.getQuantity();
        if (quantityAfterIncrement <= beerToIncrementStock.getMax()) {
            beerToIncrementStock.setQuantity(beerToIncrementStock.getQuantity() + quantityToIncrement);
            Beer incrementedBeerStock = beerRepository.save(beerToIncrementStock);
            return beerMapper.toDTO(incrementedBeerStock);
        }
        throw new BeerStockExceededException(id, quantityToIncrement);
    }*/
}
