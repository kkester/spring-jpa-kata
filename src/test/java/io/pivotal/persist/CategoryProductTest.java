package io.pivotal.persist;

import io.pivotal.persist.entities.CategoryEntity;
import io.pivotal.persist.entities.ProductEntity;
import io.pivotal.persist.repositories.CategoryRepository;
import io.pivotal.persist.repositories.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.Instant;
import java.util.List;

import static io.pivotal.persist.ProductTest.createProductEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CategoryProductTest {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ProductRepository productRepository;

	private static CategoryEntity createCategory() {
		return CategoryEntity.builder()
			.name("namer")
			.displayText("My Category has display text")
			.createdDate(Instant.now())
			.build();
	}

	@BeforeEach
	void setUp() {
		categoryRepository.deleteAll();
	}

	@AfterEach
	void tearDown() {
		categoryRepository.deleteAll();
	}

	@Test
	void saveCategory() {
		CategoryEntity categoryEntity = createCategory();

		CategoryEntity savedCategory = categoryRepository.save(categoryEntity);

		assertThat(categoryRepository.findById(savedCategory.getId())).isPresent();
	}

	@Test
	void saveCategoryMissingName() {
		CategoryEntity categoryEntity = createCategory().toBuilder()
			.name(null)
			.build();

		var error = assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(categoryEntity));
		assertThat(error.getMessage()).containsSequence("ERROR: null value in column \"name\"");
	}

	@Test
	void saveCategoryMissingCreatedDate() {
		CategoryEntity categoryEntity = createCategory().toBuilder()
			.createdDate(null)
			.build();

		var error = assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(categoryEntity));
		assertThat(error.getMessage()).containsSequence("ERROR: null value in column \"created_date\"");
	}

	@Test
	void saveCategoryWithProduct() {
		ProductEntity productEntity = createProductEntity();
		CategoryEntity categoryEntity = createCategory();
		categoryEntity.addProduct(productEntity);

		categoryRepository.save(categoryEntity);

		List<CategoryEntity> foundCategory = categoryRepository.findAll();
		assertThat(foundCategory).hasSize(1);
		assertThat(foundCategory.get(0).getProducts()).hasSize(1);
	}
}
