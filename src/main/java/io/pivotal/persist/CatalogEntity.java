package io.pivotal.persist;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
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
    private Instant createdDate;
    @ManyToMany
    private List<ProductEntity> products;
}
