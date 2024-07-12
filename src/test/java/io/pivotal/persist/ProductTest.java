package io.pivotal.persist;

import io.pivotal.persist.entities.ProductEntity;
import io.pivotal.persist.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class ProductTest {

	@Autowired
	ProductRepository productRepository;

	public static ProductEntity createProductEntity() {
		return ProductEntity.builder()
			.name("namer")
			.description("My Product has a name")
			.sku("ABC-12345-S-BL")
			.createdDate(LocalDateTime.now())
			.build();
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

		ProductEntity duplicate = ProductEntity.builder().name("bad").sku(productEntity.getSku()).createdDate(LocalDateTime.now()).build();
		var error = assertThrows(
			DataIntegrityViolationException.class,
			() -> productRepository.save(duplicate));
		assertThat(error.getMessage()).containsSequence("ERROR: duplicate key value violates unique constraint");
	}
}
