package com.softka.customer_service.mapper;

import com.softka.customer_service.model.dto.AccountRequestDto;
import com.softka.customer_service.model.dto.ClientAccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "accountId", ignore = true)
    AccountRequestDto toAccountRequestDto(ClientAccountDto clientAccountDto);
}
