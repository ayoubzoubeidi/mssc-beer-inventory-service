package com.maz.beer.inventory.service.services;

import com.maz.brewery.model.BeerOrderDto;
import com.maz.brewery.model.BeerOrderLineDto;

public interface AllocationService {

    public Boolean allocateOrder(BeerOrderDto beerOrderDto);

    public void allocateOrderLine(BeerOrderLineDto beerOrderLineDto);

}
