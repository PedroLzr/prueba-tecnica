package com.pedrosanz.pruebatec.infrastructure.rest.controller;

import com.pedrosanz.pruebatec.domain.port.in.PriceService;
import com.pedrosanz.pruebatec.domain.model.Price;
import com.pedrosanz.pruebatec.infrastructure.mapper.PriceMapper;
import com.pedrosanz.pruebatec.infrastructure.rest.api.PriceApi;
import com.pedrosanz.pruebatec.infrastructure.rest.dto.PriceResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
public class PriceController implements PriceApi {

    private final PriceService priceService;
    private final PriceMapper priceMapper;

    public PriceController(PriceService priceService, PriceMapper priceMapper) {
        this.priceService = priceService;
        this.priceMapper = priceMapper;
    }

    @Override
    public ResponseEntity<PriceResponseDTO> apiV1PricesGet(
            @RequestParam("applicationDate") LocalDateTime applicationDate,
            @RequestParam("productId") Long productId,
            @RequestParam("brandId") Long brandId) {

        log.info("Llamada a pricesGet (GET /api/v1/prices) con parámetros: applicationDate={}, productId={}, brandId={}",
                applicationDate, productId, brandId);

        Price price = priceService.getApplicablePrice(
                applicationDate,
                productId,
                brandId
        );

        PriceResponseDTO response = priceMapper.toResponseDTO(price);

        log.debug("Respuesta enviada al cliente: {}", response);
        return ResponseEntity.ok(response);
    }
}
