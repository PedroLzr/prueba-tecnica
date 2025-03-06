package com.pedrosanz.pruebatec.infrastructure.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pedrosanz.pruebatec.domain.port.in.PriceService;
import com.pedrosanz.pruebatec.domain.model.Price;
import com.pedrosanz.pruebatec.infrastructure.mapper.PriceMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PriceController.class)
@Import(PriceMapper.class)
class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PriceService priceService;

    final long productId = 35455L;
    final long brandId = 1L;
    final String currency = "EUR";

    @Test
    void testGetPriceAt10AMOn14thJune() throws Exception {
        testPriceRequest(
                LocalDateTime.of(2020, 6, 14, 10, 0, 0),
                createMockPrice(1, "2020-06-14T00:00:00", "2020-12-31T23:59:59", BigDecimal.valueOf(35.50))
        );
    }

    @Test
    void testGetPriceAt4PMOn14thJune() throws Exception {
        testPriceRequest(
                LocalDateTime.of(2020, 6, 14, 16, 0, 0),
                createMockPrice(2, "2020-06-14T15:00:00", "2020-06-14T18:30:00", BigDecimal.valueOf(25.45))
        );
    }

    @Test
    void testGetPriceAt9PMOn14thJune() throws Exception {
        testPriceRequest(
                LocalDateTime.of(2020, 6, 14, 21, 0, 0),
                createMockPrice(1, "2020-06-14T00:00:00", "2020-12-31T23:59:59", BigDecimal.valueOf(35.50))
        );
    }

    @Test
    void testGetPriceAt10AMOn15thJune() throws Exception {
        testPriceRequest(
                LocalDateTime.of(2020, 6, 15, 10, 0, 0),
                createMockPrice(3, "2020-06-15T00:00:00", "2020-06-15T11:00:00", BigDecimal.valueOf(30.50))
        );
    }

    @Test
    void testGetPriceAt9PMOn16thJune() throws Exception {
        testPriceRequest(
                LocalDateTime.of(2020, 6, 16, 21, 0, 0),
                createMockPrice(4, "2020-06-15T16:00:00", "2020-12-31T23:59:59", BigDecimal.valueOf(38.95))
        );
    }

    private void testPriceRequest(LocalDateTime applicationDate, Price mockPrice) throws Exception {
        // Formatear la fecha en ISO_LOCAL_DATE_TIME
        String formattedDate = applicationDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        Mockito.when(priceService.getApplicablePrice(applicationDate, productId, brandId)).thenReturn(mockPrice);

        mockMvc.perform(get("/api/v1/prices")
                        .param("applicationDate", formattedDate)
                        .param("productId", String.valueOf(productId))
                        .param("brandId", String.valueOf(brandId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(mockPrice.getProductId().intValue())))
                .andExpect(jsonPath("$.brandId", is(mockPrice.getBrandId().intValue())))
                .andExpect(jsonPath("$.priceList", is(mockPrice.getPriceList())))
                .andExpect(jsonPath("$.price", is(mockPrice.getPrice().doubleValue())))
                .andExpect(jsonPath("$.startDate", is(mockPrice.getStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))))
                .andExpect(jsonPath("$.endDate", is(mockPrice.getEndDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))))
                .andExpect(jsonPath("$.currency", is(mockPrice.getCurrency())));
    }

    private Price createMockPrice(Integer priceList, String startDate, String endDate, BigDecimal price) {
        return new Price(
                productId,
                brandId,
                priceList,
                LocalDateTime.parse(startDate),
                LocalDateTime.parse(endDate),
                1,
                price,
                currency
        );
    }
}
