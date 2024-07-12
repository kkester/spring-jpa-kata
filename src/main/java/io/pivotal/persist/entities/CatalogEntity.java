package io.pivotal.persist.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "CATALOG")
public class CatalogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATALOG_ID")
    private Long id;

    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDateTime createdDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "CATALOG_PRODUCT", joinColumns = @JoinColumn(name = "CATALOG_ID"), inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
    @Builder.Default
    private List<ProductEntity> products = new ArrayList<>();

    public void addProduct(ProductEntity productEntity) {
        this.products.add(productEntity);
        productEntity.getCatalogs().add(this);
    }
}
