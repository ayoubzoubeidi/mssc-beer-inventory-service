package com.maz.beer.inventory.service.services.listeners;


import com.maz.beer.inventory.service.config.JmsConfig;
import com.maz.beer.inventory.service.services.AllocationService;
import com.maz.brewery.model.BeerOrderDto;
import com.maz.brewery.model.events.DeallocateOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeallocationRequestListener {

    private final AllocationService allocationService;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.DEALLOCATE_ORDER_QUEUE)
    public void listen(DeallocateOrderRequest request) {

        allocationService.deallocateOrder(request.getBeerOrderDto());

    }
}
