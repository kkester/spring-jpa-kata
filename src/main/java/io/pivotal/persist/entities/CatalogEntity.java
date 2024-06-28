package io.pivotal.persist.entities;

import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CatalogEntity {
    private Long id;

    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private Instant createdDate;

    private List<ProductEntity> products = new ArrayList<>();

    public void addProduct(ProductEntity productEntity) {
        this.products.add(productEntity);
        productEntity.getCatalogs().add(this);
    }
}
