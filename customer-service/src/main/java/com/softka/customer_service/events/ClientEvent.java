package com.softka.customer_service.events;

import com.softka.customer_service.model.dto.EventAccountRequest;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientEvent extends Event<EventAccountRequest> {

}