package io.pivotal.persist;

import io.pivotal.persist.entities.CategoryEntity;
import io.pivotal.persist.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<CategoryEntity> getCategories() {
        return categoryRepository.findAll();
    }

    @PostMapping
    public CategoryEntity createCategory(@RequestBody CategoryEntity catalogEntity) {
        return categoryRepository.save(catalogEntity);
    }
}
