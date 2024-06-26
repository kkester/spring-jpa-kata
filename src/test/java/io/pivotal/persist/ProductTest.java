package io.pivotal.persist;

import io.pivotal.persist.entities.ProductEntity;
import io.pivotal.persist.repositories.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ProductTest {

	@Autowired
	ProductRepository productRepository;

	public static ProductEntity createProductEntity() {
		return ProductEntity.builder()
			.name("namer")
			.description("My Product has a name")
			.sku("ABC-12345-S-BL")
			.createdDate(Instant.now())
			.build();
	}

	@BeforeEach
	void setUp() {
		productRepository.deleteAll();
	}

	@AfterEach
	void tearDown() {
		productRepository.deleteAll();
	}

	@Test
	void saveProduct() {
		ProductEntity productEntity = createProductEntity();

		ProductEntity savedProduct = productRepository.save(productEntity);

		assertThat(productRepository.findById(savedProduct.getId())).isPresent();
	}

	@Test
	void saveProductMissingSku() {
		ProductEntity productEntity = createProductEntity().toBuilder()
			.sku(null)
			.build();

		var error = assertThrows(DataIntegrityViolationException.class, () -> productRepository.save(productEntity));
		assertThat(error.getMessage()).containsSequence("ERROR: null value in column \"sku\"");
	}

	@Test
	void saveProductWithDuplicateSku() {
		ProductEntity productEntity = createProductEntity();

		productRepository.save(productEntity);

		ProductEntity duplicate = ProductEntity.builder().name("bad").sku(productEntity.getSku()).createdDate(Instant.now()).build();
		var error = assertThrows(
			DataIntegrityViolationException.class,
			() -> productRepository.save(duplicate));
		assertThat(error.getMessage()).containsSequence("ERROR: duplicate key value violates unique constraint");
	}
}
