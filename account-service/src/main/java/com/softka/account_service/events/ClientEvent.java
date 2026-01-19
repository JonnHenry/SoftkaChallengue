package com.softka.account_service.events;

import com.softka.account_service.model.dto.EventAccountRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientEvent extends Event<EventAccountRequest> {

}