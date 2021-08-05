package com.gilberto.parkingapi.controller;

import com.gilberto.parkingapi.builder.AreaDTOBuilder;
import com.gilberto.parkingapi.dto.AreaDTO;
import com.gilberto.parkingapi.exception.AreaNotFoundException;
import com.gilberto.parkingapi.service.ParkingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static com.gilberto.parkingapi.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ParkingControllerTest {

    private static final String AREA_API_URL_PATH = "/api/v1/areas";
    private static final long VALID_AREA_ID = 1L;
    private static final long INVALID_AREA_ID = 2l;
    private static final String AREA_API_SUBPATH_INCREMENT_URL = "/increment";
    private static final String AREA_API_SUBPATH_DECREMENT_URL = "/decrement";

    private MockMvc mockMvc;

    @Mock
    private ParkingService parkingService;

    @InjectMocks
    private ParkingController parkingController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(parkingController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenAAreaIsCreated() throws Exception {
        // given
        AreaDTO areaDTO = AreaDTOBuilder.builder().build().toAreaDTO();

        // when
        when(parkingService.createArea(areaDTO)).thenReturn(areaDTO);

        // then
        mockMvc.perform(post(AREA_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(areaDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(areaDTO.getName())))
                .andExpect(jsonPath("$.parkingType", is(areaDTO.getParkingType())));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        AreaDTO areaDTO = AreaDTOBuilder.builder().build().toAreaDTO();
        areaDTO.setName(null);

        // then
        mockMvc.perform(post(AREA_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(areaDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETIsCalledWithValidNameThenOkStatusIsReturned() throws Exception {
        // given
        AreaDTO areaDTO = AreaDTOBuilder.builder().build().toAreaDTO();

        //when
        when(parkingService.findByName(areaDTO.getName())).thenReturn(areaDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(AREA_API_URL_PATH + "/" + areaDTO.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.name", is(areaDTO.getName())))
                .andExpect((ResultMatcher) jsonPath("$.parkingType", is(areaDTO.getParkingType().toString())));
    }

    @Test
    void whenGETIsCalledWithoutRegisteredNameThenNotFoundStatusIsReturned() throws Exception {
        // given
        AreaDTO areaDTO = AreaDTOBuilder.builder().build().toAreaDTO();

        //when
        when(parkingService.findByName(areaDTO.getName())).thenThrow(AreaNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(AREA_API_URL_PATH + "/" + areaDTO.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETListWithAreasIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        AreaDTO areaDTO = AreaDTOBuilder.builder().build().toAreaDTO();

        //when
        when(parkingService.listAll()).thenReturn(Collections.singletonList(areaDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(AREA_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$[0].name", is(areaDTO.getName())))
                .andExpect((ResultMatcher) jsonPath("$[0].type", is(areaDTO.getParkingType().toString())));
    }

    @Test
    void whenGETListWithoutAreasIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        AreaDTO areaDTO = AreaDTOBuilder.builder().build().toAreaDTO();

        //when
        when(parkingService.listAll()).thenReturn(Collections.singletonList(areaDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(AREA_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
        // given
        AreaDTO areaDTO = AreaDTOBuilder.builder().build().toAreaDTO();

        //when
        doNothing().when(parkingService).deleteById(areaDTO.getId());

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(AREA_API_URL_PATH + "/" + areaDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
        //when
        doThrow(AreaNotFoundException.class).when(parkingService).deleteById(INVALID_AREA_ID);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(AREA_API_URL_PATH + "/" + INVALID_AREA_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}