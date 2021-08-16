package my.flick.rd.springproject.intregration;

import my.flick.rd.springproject.controller.CategoryController;
import my.flick.rd.springproject.dto.CategoryDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

    @Test
    void addCategoryTest() {
        CategoryDto testCategoryDto = CategoryDto.builder().name("root1").parentId(0L).build();
        CategoryDto actualCategoryDto = testRestTemplate.postForObject(baseLink+addCategory,testCategoryDto,CategoryDto.class);
        System.out.println(actualCategoryDto);
        assertAll(
                ()->assertEquals(0,actualCategoryDto.getParentId()),
                ()->assertEquals(testCategoryDto.getName(),actualCategoryDto.getName())
        );
    }




}
