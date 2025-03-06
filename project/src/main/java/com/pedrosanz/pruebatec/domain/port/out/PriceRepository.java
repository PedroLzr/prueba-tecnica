package com.pedrosanz.pruebatec.domain.port.out;

import com.pedrosanz.pruebatec.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepository {

    /**
     * Encuentra el precio final a aplicar con la mayor prioridad.
     *
     * @param applicationDate Fecha de aplicación
     * @param productId       Identificador del producto
     * @param brandId         Identificador de la marca
     * @return Precio con mayor prioridad si existe
     */
    Optional<Price> findHighestPriorityPrice(LocalDateTime applicationDate, Long productId, Long brandId);
}
