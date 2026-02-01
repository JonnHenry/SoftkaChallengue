package com.softka.account_service.infrastructure.mapper;


import com.softka.account_service.infrastructure.dto.AccountDto;
import com.softka.account_service.domain.model.Account;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDto toDTO(Account account);
    Account toEntity(AccountDto accountDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "accountId", ignore = true)
    Account updateEntityFromDTO(AccountDto accountDto,@MappingTarget Account account);
}
