package com.pedrosanz.pruebatec.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price {

    private Long brandId;
    private Long productId;
    private Integer priceList;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer priority;
    private BigDecimal price;
    private String currency;

}