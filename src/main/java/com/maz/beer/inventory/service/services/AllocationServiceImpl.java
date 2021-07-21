package com.maz.beer.inventory.service.services;

import com.maz.beer.inventory.service.domain.BeerInventory;
import com.maz.beer.inventory.service.repositories.BeerInventoryRepository;
import com.maz.brewery.model.BeerOrderDto;
import com.maz.brewery.model.BeerOrderLineDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Service
public class AllocationServiceImpl implements AllocationService {

    private final BeerInventoryRepository beerInventoryRepository;

    @Override
    public Boolean allocateOrder(BeerOrderDto beerOrderDto) {

        AtomicInteger totalOrdered = new AtomicInteger();
        AtomicInteger totalAllocated = new AtomicInteger();

        beerOrderDto.getBeerOrderLines().forEach(
                beerOrderLineDto -> {
                    if ( (beerOrderLineDto.getOrderQuantity() != null ? beerOrderLineDto.getOrderQuantity() : 0)  -
                    (beerOrderLineDto.getQuantityAllocated() != null ? beerOrderLineDto.getQuantityAllocated() : 0) > 0 ) {
                        allocateOrderLine(beerOrderLineDto);
                        totalOrdered.set(totalOrdered.get() + beerOrderLineDto.getQuantityAllocated());
                        totalAllocated.set(totalAllocated.get() + beerOrderLineDto.getQuantityAllocated());
                    }
                }
        );

        return totalAllocated.get() == totalAllocated.get();
    }

    @Override
    public void allocateOrderLine(BeerOrderLineDto beerOrderLineDto) {

        List<BeerInventory> beerInventoryList = beerInventoryRepository.findAllByUpc(beerOrderLineDto.getUpc());

        beerInventoryList.forEach(beerInventory -> {

            int inventory = (beerInventory.getQuantityOnHand() != null ? beerInventory.getQuantityOnHand() : 0);
            int orderQuantity = beerOrderLineDto.getOrderQuantity();

            if (inventory >= orderQuantity) {

                beerInventory.setQuantityOnHand(inventory - orderQuantity);
                beerOrderLineDto.setQuantityAllocated(orderQuantity);
                beerInventoryRepository.save(beerInventory);

            }

            if (inventory < orderQuantity) {
                beerOrderLineDto.setQuantityAllocated(orderQuantity - inventory);
                beerInventoryRepository.delete(beerInventory);
            }

        });

    }

    @Override
    public void deallocateOrder(BeerOrderDto beerOrderDto) {
        beerOrderDto.getBeerOrderLines().forEach(beerOrderLineDto -> {
            BeerInventory beerInventory = BeerInventory.builder().beerId(beerOrderLineDto.getBeerId())
                    .upc(beerOrderLineDto.getUpc())
                    .quantityOnHand(beerOrderLineDto.getOrderQuantity())
                    .build();

        beerInventoryRepository.save(beerInventory);
        log.debug("Inventory Saved");
        });

    }
}
