package com.softka.account_service.mapper;


import com.softka.account_service.model.Transaction;
import com.softka.account_service.model.dto.TransactionDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionDto toDTO(Transaction transaction);
    Transaction toEntity(TransactionDto transactionDto);
}
