package com.softka.customer_service.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseErrorDto {

    private int code;
    private String error;
    private String message;
    private LocalDateTime timestamp;
}
