package com.maz.beer.inventory.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {

    public final static String NEW_INVENTORY_QUEUE = "new-inventory";
    public final static String ALLOCATE_ORDER_QUEUE = "allocate-order";
    public final static String ALLOCATE_ORDER_RESPONSE_QUEUE = "allocate-order-result";
    public final static String DEALLOCATE_ORDER_QUEUE = "deallocate-order";

    @Bean //Message serialization to json
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();

        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        converter.setObjectMapper(objectMapper);

        return converter;

    }
}
