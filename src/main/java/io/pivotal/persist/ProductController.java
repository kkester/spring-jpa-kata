package io.pivotal.persist;

import io.pivotal.persist.entities.CatalogEntity;
import io.pivotal.persist.entities.CategoryEntity;
import io.pivotal.persist.entities.ProductEntity;
import io.pivotal.persist.repositories.CatalogRepository;
import io.pivotal.persist.repositories.CategoryRepository;
import io.pivotal.persist.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final CatalogRepository catalogRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<ProductEntity> getProducts() {
        return productRepository.findAll();
    }

    @PostMapping
    public ProductEntity createProduct(@RequestBody ProductEntity productEntity) {
        if (productEntity.getInventoryStatus() != null) {
            productEntity.getInventoryStatus().setProduct(productEntity);
        }
        return productRepository.save(productEntity);
    }

    @PutMapping("/{productId}/catalogs/{catalogId}")
    public ProductEntity addProductToCatalog(@PathVariable("productId") Long productId, @PathVariable("catalogId") Long catalogId) {
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
        CatalogEntity catalogEntity = catalogRepository.findById(catalogId).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
        productEntity.addCatalog(catalogEntity);
        return productRepository.save(productEntity);
    }

    @PutMapping("/{productId}/category/{categoryId}")
    public ProductEntity addProductToCategory(@PathVariable("productId") Long productId, @PathVariable("categoryId") Long categoryId) {
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
        productEntity.setCategory(categoryEntity);
        return productRepository.save(productEntity);
    }
}
