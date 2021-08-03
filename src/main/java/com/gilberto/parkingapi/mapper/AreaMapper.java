package com.gilberto.parkingapi.mapper;

import com.gilberto.parkingapi.dto.AreaDTO;
import com.gilberto.parkingapi.entity.Area;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AreaMapper {

    AreaMapper INSTANCE = Mappers.getMapper(AreaMapper.class);

    Area toModel(AreaDTO areaDTO);

    AreaDTO toDTO(Area area);
}
