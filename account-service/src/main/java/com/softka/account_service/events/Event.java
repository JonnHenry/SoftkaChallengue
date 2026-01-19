package com.softka.account_service.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.softka.account_service.model.enums.EventType;
import lombok.Data;

import java.util.Date;

@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClientEvent.class, name = "ClientEvent"),
})
public abstract class Event<T> {

    private String id;
    private Date date;
    private EventType type;
    private T data;

}