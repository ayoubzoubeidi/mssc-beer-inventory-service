package com.maz.beer.inventory.service.services;


import com.maz.beer.inventory.service.config.JmsConfig;
import com.maz.beer.inventory.service.domain.BeerInventory;
import com.maz.beer.inventory.service.repositories.BeerInventoryRepository;
import com.maz.brewery.model.BeerDto;
import com.maz.brewery.model.events.NewInventoryEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NewInventoryService {

    private final BeerInventoryRepository beerInventoryRepository;

    @Transactional
    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
        public void listen(NewInventoryEvent event) {

        BeerDto beerDto = event.getBeerDto();

        beerInventoryRepository.save(BeerInventory.builder()
                .beerId(beerDto.getId())
                .upc(beerDto.getUpc())
                .quantityOnHand(beerDto.getQuantityOnHand()).build());
    }
}
