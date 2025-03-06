package com.pedrosanz.pruebatec.infrastructure.persistence.repository;

import com.pedrosanz.pruebatec.domain.model.Price;
import com.pedrosanz.pruebatec.domain.port.out.PriceRepository;
import com.pedrosanz.pruebatec.infrastructure.persistence.entity.PriceEntity;
import com.pedrosanz.pruebatec.infrastructure.mapper.PriceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class JpaPriceRepository implements PriceRepository {

    @PersistenceContext
    private EntityManager entityManager;
    private final PriceMapper priceMapper;

    public JpaPriceRepository(PriceMapper priceMapper) {
        this.priceMapper = priceMapper;
    }

    @Override
    public Optional<Price> findHighestPriorityPrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        log.debug("Ejecutando consulta para obtener el precio más prioritario con parámetros: productId: {}, brandId: {}, applicationDate: {}", productId, brandId, applicationDate);

        String jpql = "SELECT p FROM PriceEntity p " +
                "WHERE p.productId = :productId " +
                "AND p.brandId = :brandId " +
                "AND :applicationDate BETWEEN p.startDate AND p.endDate " +
                "ORDER BY p.priority DESC";
        TypedQuery<PriceEntity> query = entityManager.createQuery(jpql, PriceEntity.class);
        query.setParameter("applicationDate", applicationDate);
        query.setParameter("productId", productId);
        query.setParameter("brandId", brandId);

        List<PriceEntity> priceEntities = query.setMaxResults(1).getResultList();
        return priceEntities.stream().findFirst().map(priceMapper::toDomain);
    }

}
