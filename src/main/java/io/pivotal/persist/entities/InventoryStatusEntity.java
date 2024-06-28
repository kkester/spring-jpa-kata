package io.pivotal.persist.entities;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class InventoryStatusEntity {
    private Long productId;
    private ProductEntity product;
    private InventoryStatus status;
    private Integer quantity;
}
