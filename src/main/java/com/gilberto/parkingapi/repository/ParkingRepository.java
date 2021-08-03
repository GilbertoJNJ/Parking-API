package com.gilberto.parkingapi.repository;

import com.gilberto.parkingapi.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingRepository extends JpaRepository<Area, Long> {
    Optional<Area> findByName(String name);
}
