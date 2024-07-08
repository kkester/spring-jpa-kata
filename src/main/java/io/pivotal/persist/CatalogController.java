package io.pivotal.persist;

import io.pivotal.persist.entities.CatalogEntity;
import io.pivotal.persist.repositories.CatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalogs")
public class CatalogController {

    private final CatalogRepository catalogRepository;

    @GetMapping
    public List<CatalogEntity> getCatalogs() {
        return catalogRepository.findAll();
    }

    @PostMapping
    public CatalogEntity createCatalog(@RequestBody CatalogEntity catalogEntity) {
        return catalogRepository.save(catalogEntity);
    }
}
