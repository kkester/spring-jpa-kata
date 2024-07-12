package io.pivotal.persist.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "PRODUCT")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;

    private String name;

    private String description;

    private String sku;

    private LocalDateTime createdDate;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private InventoryStatusEntity inventoryStatus;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private CategoryEntity category;

    @ManyToMany//(mappedBy = "products", cascade = CascadeType.ALL)
    @Builder.Default
    private List<CatalogEntity> catalogs = new ArrayList<>();

    public void applyInventoryStatus(InventoryStatusEntity inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
        inventoryStatus.setProduct(this);
    }

    public void addCatalog(CatalogEntity catalogEntity) {
        this.catalogs.add(catalogEntity);
        catalogEntity.getProducts().add(this);
    }
}
