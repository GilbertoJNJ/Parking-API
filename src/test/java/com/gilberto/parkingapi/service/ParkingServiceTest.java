package com.gilberto.parkingapi.service;

import com.gilberto.parkingapi.builder.AreaDTOBuilder;
import com.gilberto.parkingapi.dto.AreaDTO;
import com.gilberto.parkingapi.entity.Area;
import com.gilberto.parkingapi.exception.AreaAlreadyRegisteredException;
import com.gilberto.parkingapi.exception.AreaNotFoundException;
import com.gilberto.parkingapi.mapper.AreaMapper;
import com.gilberto.parkingapi.repository.ParkingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {

    private static final long INVALID_AREA_ID = 1L;

    @Mock
    private ParkingRepository parkingRepository;

    private AreaMapper areaMapper = AreaMapper.INSTANCE;

    @InjectMocks
    private ParkingService parkingService;

    @Test
    void whenAreaInformedThenItShouldBeCreated() throws AreaAlreadyRegisteredException {
        // given
        AreaDTO expectedAreaDTO = AreaDTOBuilder.builder().build().toAreaDTO();
        Area expectedSavedArea = areaMapper.toModel(expectedAreaDTO);

        // when
        when(parkingRepository.findByName(expectedAreaDTO.getName())).thenReturn(Optional.empty());
        when(parkingRepository.save(expectedSavedArea)).thenReturn(expectedSavedArea);

        //then
        AreaDTO createdAreaDTO = parkingService.createArea(expectedAreaDTO);

        assertThat(createdAreaDTO.getId(), is(equalTo(expectedAreaDTO.getId())));
        assertThat(createdAreaDTO.getName(), is(equalTo(expectedAreaDTO.getName())));
        assertThat(createdAreaDTO.getQuantity(), is(equalTo(expectedAreaDTO.getQuantity())));
    }

    @Test
    void whenAlreadyRegisteredAreaInformedThenAnExceptionShouldBeThrown() {
        // given
        AreaDTO expectedAreaDTO = AreaDTOBuilder.builder().build().toAreaDTO();
        Area duplicatedArea = areaMapper.toModel(expectedAreaDTO);

        // when
        when(parkingRepository.findByName(expectedAreaDTO.getName())).thenReturn(Optional.of(duplicatedArea));

        // then
        assertThrows(AreaAlreadyRegisteredException.class, () -> parkingService.createArea(expectedAreaDTO));
    }

    @Test
    void whenValidAreaNameIsGivenThenReturnAnArea() throws AreaNotFoundException {
        // given
        AreaDTO expectedFoundAreaDTO = AreaDTOBuilder.builder().build().toAreaDTO();
        Area expectedFoundArea = areaMapper.toModel(expectedFoundAreaDTO);

        // when
        when(parkingRepository.findByName(expectedFoundAreaDTO.getName())).thenReturn(Optional.of(expectedFoundArea));

        // then
        AreaDTO foundAreaDTO = parkingService.findByName(expectedFoundAreaDTO.getName());

        assertThat(foundAreaDTO, is(equalTo(expectedFoundAreaDTO)));
    }

    @Test
    void whenNotRegisteredAreaNameIsGivenThenThrowAnException() {
        // given
        AreaDTO expectedFoundAreaDTO = AreaDTOBuilder.builder().build().toAreaDTO();

        // when
        when(parkingRepository.findByName(expectedFoundAreaDTO.getName())).thenReturn(Optional.empty());

        // then
        assertThrows(AreaNotFoundException.class, () -> parkingService.findByName(expectedFoundAreaDTO.getName()));
    }

    @Test
    void whenListAreaIsCalledThenReturnAListOfAreas() {
        // given
        AreaDTO expectedFoundAreaDTO = AreaDTOBuilder.builder().build().toAreaDTO();
        Area expectedFoundArea = areaMapper.toModel(expectedFoundAreaDTO);

        //when
        when(parkingRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundArea));

        //then
        List<AreaDTO> foundListAreasDTO = parkingService.listAll();

        assertThat(foundListAreasDTO, is(not(empty())));
        assertThat(foundListAreasDTO.get(0), is(equalTo(expectedFoundAreaDTO)));
    }

    @Test
    void whenListAreaIsCalledThenReturnAnEmptyListOfAreas() {
        //when
        when(parkingRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        //then
        List<AreaDTO> foundListBeersDTO = parkingService.listAll();

        assertThat(foundListBeersDTO, is(empty()));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenAnAreaShouldBeDeleted() throws AreaNotFoundException {
        // given
        AreaDTO expectedDeletedAreaDTO = AreaDTOBuilder.builder().build().toAreaDTO();
        Area expectedDeletedArea = areaMapper.toModel(expectedDeletedAreaDTO);

        // when
        when(parkingRepository.findById(expectedDeletedAreaDTO.getId())).thenReturn(Optional.of(expectedDeletedArea));
        doNothing().when(parkingRepository).deleteById(expectedDeletedAreaDTO.getId());

        // then
        parkingService.deleteById(expectedDeletedAreaDTO.getId());

        verify(parkingRepository, times(1)).findById(expectedDeletedAreaDTO.getId());
        verify(parkingRepository, times(1)).deleteById(expectedDeletedAreaDTO.getId());
    }

}
