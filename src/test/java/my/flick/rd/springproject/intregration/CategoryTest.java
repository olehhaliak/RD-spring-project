package my.flick.rd.springproject.intregration;

import my.flick.rd.springproject.controller.CategoryController;
import my.flick.rd.springproject.dto.CategoryDto;
import my.flick.rd.springproject.model.Category;
import my.flick.rd.springproject.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static my.flick.rd.springproject.test.utils.CategoryTestData.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class CategoryTest {

    @Value("http://localhost:${local.server.port}")
    private String baseLink;
    private String addCategory = linkTo(methodOn(CategoryController.class).addCategory(null)).toString();

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    CategoryRepository categoryRepository;

    @AfterEach
    void clearDB() {
        categoryRepository.deleteAll();
    }

    @Test
    void addCategoryTest() {
        CategoryDto testCategoryDto = CategoryDto.builder().name("root1").parentId(0L).build();
        CategoryDto actualCategoryDto = testRestTemplate.postForObject(baseLink + addCategory, testCategoryDto, CategoryDto.class);
        System.out.println(actualCategoryDto);
        assertAll(
                () -> assertEquals(0, actualCategoryDto.getParentId()),
                () -> assertEquals(testCategoryDto.getName(), actualCategoryDto.getName())
        );
    }

    @Test
    void getCategoryById() {
        Category testCategory = categoryRepository.save(CATEGORY);
        String getByIdLink = baseLink + linkTo(methodOn(CategoryController.class).getCategoryById(testCategory.getId()));
        CategoryDto actualCategoryDto = testRestTemplate.getForObject(getByIdLink, CategoryDto.class);
        assertAll(
                () -> assertEquals(testCategory.getParent().getId(), actualCategoryDto.getParentId()),
                () -> assertEquals(testCategory.getName(), actualCategoryDto.getName())
        );
    }

    @Test
    void getRootCategories() {
        Category testCategory = Category.builder().name("any").parent(null).build();
        testCategory = categoryRepository.save(testCategory);
        CategoryDto testCategoryDto = new CategoryDto(testCategory.getId(), testCategory.getName(), 0);
        String getRootCategories = baseLink + linkTo(methodOn(CategoryController.class).getRootCategories());
        List<CategoryDto> categoryDtoList = Arrays.asList(testRestTemplate.getForObject(getRootCategories, CategoryDto[].class));
        assertThat(categoryDtoList, contains(testCategoryDto));
    }

    @Test
    void getSubcategories() {
        Category testCategory = CATEGORY;
        testCategory = categoryRepository.save(testCategory);
        CategoryDto testCategoryDto = new CategoryDto(testCategory.getId(), testCategory.getName(), testCategory.getParent().getId());
        String getRootCategories = baseLink + linkTo(methodOn(CategoryController.class).getSubcategories(testCategory.getParent().getId()));
        List<CategoryDto> categoryDtoList = Arrays.asList(testRestTemplate.getForObject(getRootCategories, CategoryDto[].class));
        assertThat(categoryDtoList, contains(testCategoryDto));
    }

    @Test
    void deleteCategory() {
        Category testCategory = CATEGORY;
        testCategory = categoryRepository.save(testCategory);
        String deleteCategoryLink = baseLink + linkTo(methodOn(CategoryController.class).deleteCategory(testCategory.getId()));
        testRestTemplate.delete(deleteCategoryLink);
        assertTrue(categoryRepository.findById(testCategory.getId()).isEmpty());
    }

    @Test
    void updateCategory() {
        Category testCategory = categoryRepository.save(CATEGORY);
        CategoryDto testCategoryDto = CategoryDto.builder()
                .id(testCategory.getId())
                .name("updated name")
                .parentId(testCategory.getParent().getId())
                .build();
        String updateCategoryLink = baseLink + linkTo(methodOn(CategoryController.class).updateCategory(testCategory.getId(), null));
        CategoryDto actualCategoryDto = testRestTemplate.exchange(updateCategoryLink, HttpMethod.PUT,new HttpEntity<CategoryDto>(testCategoryDto),CategoryDto.class).getBody();
        System.out.println(actualCategoryDto);
        assertAll(
                () -> assertEquals(testCategory.getParent().getId(), actualCategoryDto.getParentId()),
                () -> assertEquals("updated name", actualCategoryDto.getName())
        );
    }
}
