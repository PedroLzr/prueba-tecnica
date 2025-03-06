package com.pedrosanz.pruebatec.application.service.impl;

import com.pedrosanz.pruebatec.domain.port.in.PriceService;
import com.pedrosanz.pruebatec.domain.model.Price;
import com.pedrosanz.pruebatec.domain.port.out.PriceRepository;
import com.pedrosanz.pruebatec.application.exception.PriceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

    public PriceServiceImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    /**
     * Obtiene el precio final aplicable a un producto, marca y fecha.
     *
     * @param applicationDate Fecha de aplicación
     * @param productId       Identificador del producto
     * @param brandId         Identificador de la marca
     * @return El precio con mayor prioridad aplicable
     * @throws PriceNotFoundException Si no se encuentra un precio aplicable
     */
    @Override
    public Price getApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        log.trace("Ejecutando getApplicablePrice");

        Optional<Price> highestPriorityPrice = priceRepository.findHighestPriorityPrice(applicationDate, productId, brandId);

        return highestPriorityPrice.orElseThrow(() ->
                new PriceNotFoundException("No se encontró un precio aplicable para los parámetros proporcionados"));
    }
}
