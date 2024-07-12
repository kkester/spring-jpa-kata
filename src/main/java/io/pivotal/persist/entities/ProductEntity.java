package io.pivotal.persist.entities;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    private Long id;

    private String name;

    private String description;

    private String sku;

    private LocalDateTime createdDate;

    private InventoryStatusEntity inventoryStatus;

    private CategoryEntity category;

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
