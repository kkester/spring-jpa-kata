package io.pivotal.persist.repositories;

import io.pivotal.persist.entities.CatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<CatalogEntity,Long> {
}
