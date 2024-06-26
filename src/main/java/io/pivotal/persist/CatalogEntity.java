package io.pivotal.persist;

import lombok.*;

import java.time.Instant;
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
    private Instant createdDate;
    private List<ProductEntity> products;
}
