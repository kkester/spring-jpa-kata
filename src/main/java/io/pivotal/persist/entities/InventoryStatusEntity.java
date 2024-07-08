package io.pivotal.persist.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "INVENTORY_STATUS")
public class InventoryStatusEntity {
    @Id
    @JsonIgnore
    private Long productId;
    @OneToOne
    @JoinColumn(name = "product_id")
    @MapsId
    @JsonIgnore
    private ProductEntity product;
    private InventoryStatus status;
    private Integer quantity;
}
