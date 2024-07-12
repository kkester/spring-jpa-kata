package io.pivotal.persist;

import io.pivotal.persist.entities.CatalogEntity;
import io.pivotal.persist.entities.ProductEntity;
import io.pivotal.persist.repositories.CatalogRepository;
import io.pivotal.persist.repositories.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static io.pivotal.persist.ProductTest.createProductEntity;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CatalogProductTest {

	@Autowired
	CatalogRepository catalogRepository;

	@Autowired
	ProductRepository productRepository;

	private static CatalogEntity createCatalogEntity() {
		return CatalogEntity.builder()
			.name("namer")
			.description("My Catalog has a name")
			.startDate(LocalDate.now())
			.endDate(LocalDate.now().plusYears(1))
			.createdDate(LocalDateTime.now())
			.build();
	}

	@BeforeEach
	void setUp() {
		catalogRepository.deleteAll();
	}

	@AfterEach
	void tearDown() {
		catalogRepository.deleteAll();
	}

	@Test
	void saveCatalog() {
		CatalogEntity catalogEntity = createCatalogEntity();

		catalogRepository.save(catalogEntity);

		List<CatalogEntity> catalogEntities = catalogRepository.findAll();
		assertThat(catalogEntities).hasSize(1);
	}

	@Test
	@Transactional
	void saveCatalogWithProduct() {
		ProductEntity productEntity = createProductEntity();
		CatalogEntity catalogEntity = createCatalogEntity();
		catalogEntity.addProduct(productEntity);

		catalogRepository.save(catalogEntity);

		List<CatalogEntity> foundCatalog = catalogRepository.findAll();
		assertThat(foundCatalog).hasSize(1);
		assertThat(foundCatalog.get(0).getProducts()).hasSize(1);
	}

	@Test
	@Transactional
	void saveProductWithCatalog() {
		CatalogEntity catalogEntity = createCatalogEntity();
		ProductEntity productEntity = createProductEntity();
		productEntity.addCatalog(catalogEntity);

		productRepository.save(productEntity);

		List<ProductEntity> foundProduct = productRepository.findAll();
		assertThat(foundProduct).hasSize(1);
		assertThat(foundProduct.get(0).getCatalogs()).hasSize(1);
	}
}
