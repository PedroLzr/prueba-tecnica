package com.pedrosanz.pruebatec.infrastructure.mapper;

import com.pedrosanz.pruebatec.domain.model.Price;
import com.pedrosanz.pruebatec.infrastructure.persistence.entity.PriceEntity;
import com.pedrosanz.pruebatec.infrastructure.rest.dto.PriceResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PriceMapper {

    public Price toDomain(PriceEntity priceEntity) {
        return new Price(
                priceEntity.getBrandId(),
                priceEntity.getProductId(),
                priceEntity.getPriceList(),
                priceEntity.getStartDate(),
                priceEntity.getEndDate(),
                priceEntity.getPriority(),
                priceEntity.getPrice(),
                priceEntity.getCurrency()
        );
    }

    public PriceResponseDTO toResponseDTO(Price price){
        PriceResponseDTO responseDTO = new PriceResponseDTO();
        responseDTO.setProductId(price.getProductId());
        responseDTO.setBrandId(price.getBrandId());
        responseDTO.setPriceList(price.getPriceList());
        responseDTO.setStartDate(price.getStartDate());
        responseDTO.setEndDate(price.getEndDate());
        responseDTO.setPrice(price.getPrice());
        responseDTO.setCurrency(price.getCurrency());

        return responseDTO;
    }

}
