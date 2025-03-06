package com.pedrosanz.pruebatec.application.service;

import com.pedrosanz.pruebatec.domain.port.in.PriceService;
import com.pedrosanz.pruebatec.application.service.impl.PriceServiceImpl;
import com.pedrosanz.pruebatec.domain.model.Price;
import com.pedrosanz.pruebatec.domain.port.out.PriceRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PriceServiceTest {

    private final PriceRepository priceRepository = mock(PriceRepository.class);
    private final PriceService priceService = new PriceServiceImpl(priceRepository);

    @Test
    void testGetApplicablePriceAt10AMOn14thJune() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        Price expectedPrice = new Price(brandId, productId, 1,
                applicationDate.minusDays(1), applicationDate.plusDays(1),
                0, BigDecimal.valueOf(35.50), "EUR");
        when(priceRepository.findHighestPriorityPrice(applicationDate, productId, brandId))
                .thenReturn(Optional.of(expectedPrice));

        Price actualPrice = priceService.getApplicablePrice(applicationDate, productId, brandId);

        assertNotNull(actualPrice);
        assertEquals(expectedPrice, actualPrice);
        verify(priceRepository, times(1)).findHighestPriorityPrice(applicationDate, productId, brandId);
    }

    @Test
    void testGetApplicablePriceAt4PMOn14thJune() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        Price expectedPrice = new Price(brandId, productId, 2,
                LocalDateTime.of(2020, 6, 14, 15, 0), LocalDateTime.of(2020, 6, 14, 18, 30),
                1, BigDecimal.valueOf(25.45), "EUR");
        when(priceRepository.findHighestPriorityPrice(applicationDate, productId, brandId))
                .thenReturn(Optional.of(expectedPrice));

        Price actualPrice = priceService.getApplicablePrice(applicationDate, productId, brandId);

        assertNotNull(actualPrice);
        assertEquals(expectedPrice, actualPrice);
        verify(priceRepository, times(1)).findHighestPriorityPrice(applicationDate, productId, brandId);
    }

    @Test
    void testGetApplicablePriceAt9PMOn14thJune() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 21, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        Price expectedPrice = new Price(brandId, productId, 1,
                LocalDateTime.of(2020, 6, 14, 0, 0), LocalDateTime.of(2020, 12, 31, 23, 59),
                0, BigDecimal.valueOf(35.50), "EUR");
        when(priceRepository.findHighestPriorityPrice(applicationDate, productId, brandId))
                .thenReturn(Optional.of(expectedPrice));

        Price actualPrice = priceService.getApplicablePrice(applicationDate, productId, brandId);

        assertNotNull(actualPrice);
        assertEquals(expectedPrice, actualPrice);
        verify(priceRepository, times(1)).findHighestPriorityPrice(applicationDate, productId, brandId);
    }

    @Test
    void testGetApplicablePriceAt10AMOn15thJune() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 15, 10, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        Price expectedPrice = new Price(brandId, productId, 3,
                LocalDateTime.of(2020, 6, 15, 0, 0), LocalDateTime.of(2020, 6, 15, 11, 0),
                1, BigDecimal.valueOf(30.50), "EUR");
        when(priceRepository.findHighestPriorityPrice(applicationDate, productId, brandId))
                .thenReturn(Optional.of(expectedPrice));

        Price actualPrice = priceService.getApplicablePrice(applicationDate, productId, brandId);

        assertNotNull(actualPrice);
        assertEquals(expectedPrice, actualPrice);
        verify(priceRepository, times(1)).findHighestPriorityPrice(applicationDate, productId, brandId);
    }

    @Test
    void testGetApplicablePriceAt9PMOn16thJune() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 16, 21, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        Price expectedPrice = new Price(brandId, productId, 4,
                LocalDateTime.of(2020, 6, 15, 16, 0), LocalDateTime.of(2020, 12, 31, 23, 59),
                1, BigDecimal.valueOf(38.95), "EUR");
        when(priceRepository.findHighestPriorityPrice(applicationDate, productId, brandId))
                .thenReturn(Optional.of(expectedPrice));

        Price actualPrice = priceService.getApplicablePrice(applicationDate, productId, brandId);

        assertNotNull(actualPrice);
        assertEquals(expectedPrice, actualPrice);
        verify(priceRepository, times(1)).findHighestPriorityPrice(applicationDate, productId, brandId);
    }
}
