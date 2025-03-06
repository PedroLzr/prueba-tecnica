package com.pedrosanz.pruebatec.e2e;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class PriceE2ETest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    void testGetPriceAt10AMOn14thJune() {
        given()
                .queryParam("applicationDate", "2020-06-14T10:00:00")
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
                .when()
                .get("/api/v1/prices")
                .then()
                .statusCode(200)
                .body("productId", equalTo(35455))
                .body("brandId", equalTo(1))
                .body("priceList", equalTo(1))
                .body("price", equalTo(35.50f))
                .body("startDate", equalTo("2020-06-14T00:00:00"))
                .body("endDate", equalTo("2020-12-31T23:59:59"))
                .body("currency", equalTo("EUR"));
    }

    @Test
    void testGetPriceAt4PMOn14thJune() {
        given()
                .queryParam("applicationDate", "2020-06-14T16:00:00")
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
                .when()
                .get("/api/v1/prices")
                .then()
                .statusCode(200)
                .body("productId", equalTo(35455))
                .body("brandId", equalTo(1))
                .body("priceList", equalTo(2))
                .body("price", equalTo(25.45f))
                .body("startDate", equalTo("2020-06-14T15:00:00"))
                .body("endDate", equalTo("2020-06-14T18:30:00"))
                .body("currency", equalTo("EUR"));
    }

    @Test
    void testGetPriceAt9PMOn14thJune() {
        given()
                .queryParam("applicationDate", "2020-06-14T21:00:00")
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
                .when()
                .get("/api/v1/prices")
                .then()
                .statusCode(200)
                .body("productId", equalTo(35455))
                .body("brandId", equalTo(1))
                .body("priceList", equalTo(1))
                .body("price", equalTo(35.50f))
                .body("startDate", equalTo("2020-06-14T00:00:00"))
                .body("endDate", equalTo("2020-12-31T23:59:59"))
                .body("currency", equalTo("EUR"));
    }

    @Test
    void testGetPriceAt10AMOn15thJune() {
        given()
                .queryParam("applicationDate", "2020-06-15T10:00:00")
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
                .when()
                .get("/api/v1/prices")
                .then()
                .statusCode(200)
                .body("productId", equalTo(35455))
                .body("brandId", equalTo(1))
                .body("priceList", equalTo(3))
                .body("price", equalTo(30.50f))
                .body("startDate", equalTo("2020-06-15T00:00:00"))
                .body("endDate", equalTo("2020-06-15T11:00:00"))
                .body("currency", equalTo("EUR"));
    }

    @Test
    void testGetPriceAt9PMOn16thJune() {
        given()
                .queryParam("applicationDate", "2020-06-16T21:00:00")
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
                .when()
                .get("/api/v1/prices")
                .then()
                .statusCode(200)
                .body("productId", equalTo(35455))
                .body("brandId", equalTo(1))
                .body("priceList", equalTo(4))
                .body("price", equalTo(38.95f))
                .body("startDate", equalTo("2020-06-15T16:00:00"))
                .body("endDate", equalTo("2020-12-31T23:59:59"))
                .body("currency", equalTo("EUR"));
    }

    @Test
    void testGetPriceWithNullApplicationDate() {
        given()
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
                .when()
                .get("/api/v1/prices")
                .then()
                .statusCode(400)
                .body("status", equalTo(400))
                .body("message", equalTo("applicationDate: must not be null"));
    }

    @Test
    void testGetPriceWithInvalidProductId() {
        given()
                .queryParam("applicationDate", "2020-06-14T10:00:00")
                .queryParam("productId", -1)
                .queryParam("brandId", 1)
                .when()
                .get("/api/v1/prices")
                .then()
                .statusCode(400)
                .body("status", equalTo(400))
                .body("message", equalTo("apiV1PricesGet.productId: must be greater than or equal to 1"));
    }

    @Test
    void testGetPriceWithNullBrandId() {
        given()
                .queryParam("applicationDate", "2020-06-14T10:00:00")
                .queryParam("productId", 35455)
                .when()
                .get("/api/v1/prices")
                .then()
                .statusCode(400)
                .body("status", equalTo(400))
                .body("message", equalTo("brandId: must not be null"));
    }

    @Test
    void testGetPriceNotFound() {
        given()
                .queryParam("applicationDate", "2030-01-01T00:00:00")
                .queryParam("productId", 99999)
                .queryParam("brandId", 99)
                .when()
                .get("/api/v1/prices")
                .then()
                .statusCode(404)
                .body("status", equalTo(404))
                .body("message", equalTo("No se encontró un precio aplicable para los parámetros proporcionados"));
    }

    @Test
    void testGetPriceWithMalformedRequest() {
        given()
                .queryParam("applicationDate", "2020-06-14T10:00:00")
                .queryParam("productId", "invalidProductId")
                .queryParam("brandId", 1)
                .when()
                .get("/api/v1/prices")
                .then()
                .statusCode(400)
                .body("status", equalTo(400))
                .body("message", equalTo("productId: must be a valid number"));
    }
}
