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
public class CategoryEntity {
    private Long id;

    private String name;

    private String displayText;

    private LocalDateTime createdDate;
    
    @Builder.Default
    private List<ProductEntity> products = new ArrayList<>();

    public void addProduct(ProductEntity productEntity) {
        products.add(productEntity);
        productEntity.setCategory(this);
    }
}
