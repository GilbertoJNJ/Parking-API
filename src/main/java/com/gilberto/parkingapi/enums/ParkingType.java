package com.gilberto.parkingapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ParkingType {
    CAR("Car"),
    MOTORCYCLE("Motorcycle"),
    BIKE("Bike");

    private final String description;
}
