package com.softka.events;


import com.softka.dto.EventAccountRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientEvent extends Event<EventAccountRequest> {

}