package com.pedrosanz.pruebatec.domain.port.in;

import com.pedrosanz.pruebatec.domain.model.Price;

import java.time.LocalDateTime;

public interface PriceService {

    Price getApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId);

}
