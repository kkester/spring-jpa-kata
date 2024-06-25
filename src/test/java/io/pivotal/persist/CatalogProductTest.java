package io.pivotal.persist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CatalogProductTest {

	@Autowired
	CatalogRepository catalogRepository;

	@Autowired
	ProductRepository productRepository;

	@Test
	void saveCatalog() {
		CatalogEntity catalogEntity = CatalogEntity.builder()
			.name("namer")
			.description("My Catalog has a name")
			.createdDate(Instant.now())
			.build();

		CatalogEntity savedCatalog = catalogRepository.save(catalogEntity);

		assertThat(catalogRepository.findById(savedCatalog.getId())).isPresent();
	}

	@Test
	void saveProduct() {
		ProductEntity productEntity = ProductEntity.builder()
			.name("namer")
			.description("My Product has a name")
			.sku("ABC-12345-S-BL")
			.createdDate(Instant.now())
			.build();

		ProductEntity savedProduct = productRepository.save(productEntity);

		assertThat(productRepository.findById(savedProduct.getId())).isPresent();
	}

	@Test
	void saveCatalogWithProduct() {
		ProductEntity productEntity = ProductEntity.builder()
			.name("namer")
			.description("My Product has a name")
			.sku("ABC-12345-S-BL")
			.createdDate(Instant.now())
			.build();

		CatalogEntity catalogEntity = CatalogEntity.builder()
			.name("namer")
			.description("My Catalog has a name")
			.createdDate(Instant.now())
			.products(List.of(productEntity))
			.build();

		CatalogEntity savedCatalog = catalogRepository.save(catalogEntity);

		Optional<CatalogEntity> foundCatalog = catalogRepository.findById(savedCatalog.getId());
		assertThat(foundCatalog).isPresent();
		assertThat(foundCatalog.get().getProducts()).hasSize(1);
	}

	@Test
	void saveProductWithCatalog() {
		CatalogEntity catalogEntity = CatalogEntity.builder()
			.name("namer")
			.description("My Catalog has a name")
			.createdDate(Instant.now())
			.build();

		ProductEntity productEntity = ProductEntity.builder()
			.name("namer")
			.description("My Product has a name")
			.sku("ABC-12345-S-BL")
			.createdDate(Instant.now())
			.catalogs(List.of(catalogEntity))
			.build();

		ProductEntity savedProduct = productRepository.save(productEntity);

		Optional<ProductEntity> foundProduct = productRepository.findById(savedProduct.getId());
		assertThat(foundProduct).isPresent();
		assertThat(foundProduct.get().getCatalogs()).hasSize(1);
	}
}
