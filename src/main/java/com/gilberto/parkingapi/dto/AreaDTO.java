package com.gilberto.parkingapi.dto;

import com.gilberto.parkingapi.enums.ParkingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AreaDTO {

    private Long id;

    @NotNull
    @Size(min = 1,max = 100)
    private String name;

    @NotNull
    @Max(500)
    private int maxQuantity;

    @NotNull
    @Max(100)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ParkingType parkingType;

}
