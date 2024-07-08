package io.pivotal.persist;

import io.pivotal.persist.entities.InventoryStatus;
import io.pivotal.persist.entities.InventoryStatusEntity;
import io.pivotal.persist.entities.ProductEntity;
import io.pivotal.persist.repositories.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.pivotal.persist.ProductTest.createProductEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class ProductInventoryTest {

    @Autowired
    ProductRepository productRepository;

    private static InventoryStatusEntity createInventoryStatusEntity() {
        return InventoryStatusEntity.builder()
            .status(InventoryStatus.IN_STOCK)
            .quantity(10)
            .build();
    }

    @Test
    void saveProductWithInventory() {
        InventoryStatusEntity inventoryStatus = createInventoryStatusEntity();
        ProductEntity productEntity = createProductEntity();
        productEntity.applyInventoryStatus(inventoryStatus);

        productRepository.save(productEntity);

        List<ProductEntity> actualProducts = productRepository.findAll();
        assertThat(actualProducts).hasSize(1);
        ProductEntity foundProduct = actualProducts.get(0);
        assertThat(foundProduct.getInventoryStatus()).isNotNull();
        assertThat(foundProduct.getInventoryStatus().getProduct()).isEqualTo(foundProduct);
    }

    @Test
    void saveProductWithInventoryWithNegativeQuantity() {
        InventoryStatusEntity inventoryStatus = createInventoryStatusEntity().toBuilder()
            .quantity(-1)
            .build();
        ProductEntity productEntity = createProductEntity();
        productEntity.applyInventoryStatus(inventoryStatus);

        var error = assertThrows(DataIntegrityViolationException.class, () -> productRepository.save(productEntity));
        assertThat(error.getMessage()).containsSequence("ERROR: new row for relation \"inventory_status\"");
    }
}
