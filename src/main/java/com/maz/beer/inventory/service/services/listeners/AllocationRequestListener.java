package com.maz.beer.inventory.service.services.listeners;

import com.maz.beer.inventory.service.config.JmsConfig;
import com.maz.beer.inventory.service.services.AllocationService;
import com.maz.brewery.model.events.AllocateOrderRequest;
import com.maz.brewery.model.events.AllocateOrderResult;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AllocationRequestListener {

    private final AllocationService allocationService;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
    public void listen(AllocateOrderRequest allocateOrderRequest) {

        AllocateOrderResult.AllocateOrderResultBuilder builder = AllocateOrderResult.builder();
        builder.beerOrder(allocateOrderRequest.getBeerOrder());

        try {
            Boolean totalAllocation = allocationService.allocateOrder(allocateOrderRequest.getBeerOrder());

            builder.allocationError(false);
            builder.pendingInventory(!allocationService.allocateOrder(allocateOrderRequest.getBeerOrder()));

        } catch (Exception e) {
            builder.allocationError(true);
        }

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE, builder.build());
    }

}
