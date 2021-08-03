package com.gilberto.parkingapi.builder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gilberto.parkingapi.dto.AreaDTO;
import com.gilberto.parkingapi.enums.ParkingType;
import lombok.Builder;

@Builder
public class AreaDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Area1";

    @Builder.Default
    private int maxQuantity = 50;

    @Builder.Default
    private int quantity = 10;

    @Builder.Default
    private ParkingType type = ParkingType.CAR;

    public AreaDTO toAreaDTO() {
        return new AreaDTO(
                id,
                name,
                maxQuantity,
                quantity,
                type);
    }
}

