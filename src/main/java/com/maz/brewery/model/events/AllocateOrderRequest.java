package com.maz.brewery.model.events;

import com.maz.brewery.model.BeerOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllocateOrderRequest implements Serializable {

    static final long serialVersionUID = -832249231503626290L;
    private BeerOrderDto beerOrder;

}
