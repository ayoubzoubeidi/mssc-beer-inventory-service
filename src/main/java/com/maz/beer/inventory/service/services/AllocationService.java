package com.maz.beer.inventory.service.services;

import com.maz.brewery.model.BeerOrderDto;
import com.maz.brewery.model.BeerOrderLineDto;

public interface AllocationService {

    Boolean allocateOrder(BeerOrderDto beerOrderDto);

    void allocateOrderLine(BeerOrderLineDto beerOrderLineDto);

    void deallocateOrder(BeerOrderDto beerOrderDto);


}
