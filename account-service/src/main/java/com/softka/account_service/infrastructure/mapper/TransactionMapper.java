package com.softka.account_service.infrastructure.mapper;


import com.softka.account_service.domain.model.Transaction;
import com.softka.account_service.infrastructure.dto.TransactionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);


    TransactionDto toDTO(Transaction transaction);
    @Mapping(target  = "account", ignore = true)
    Transaction toEntity(TransactionDto transactionDto);

}
