package com.softka.customer_service.mapper;


import com.softka.customer_service.model.Client;
import com.softka.customer_service.model.dto.ClientDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    ClientDto toDTO(Client client);
    Client toEntity(ClientDto clientDto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateEntityFromDTO(ClientDto clientDto,@MappingTarget Client client);
}
