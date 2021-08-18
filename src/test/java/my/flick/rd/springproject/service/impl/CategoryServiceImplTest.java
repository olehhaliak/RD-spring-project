package my.flick.rd.springproject.service.impl;

import my.flick.rd.springproject.exception.CategoryNotFoundException;
import my.flick.rd.springproject.exception.SelfReferencingException;
import my.flick.rd.springproject.repository.CategoryRepository;
import my.flick.rd.springproject.util.mapper.CategoryDtoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static my.flick.rd.springproject.test.utils.CategoryTestData.*;
@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    CategoryRepository categoryRepository;
    @Mock
    CategoryDtoMapper categoryDtoMapper;
    @InjectMocks
    CategoryServiceImpl categoryService;

    @Test
    void getCategoryByIdTest() {
        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.of(CATEGORY));
        when(categoryDtoMapper.mapToDto(CATEGORY)).thenReturn(CATEGORY_DTO);
        assertThat(categoryService.getCategoryById(CATEGORY_ID),is(equalTo(CATEGORY_DTO)));
    }

    @Test
    void getCategoryByIdNotExistsTest() {
        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class,()->categoryService.getCategoryById(CATEGORY_ID));
    }

    @Test

    void addCategoryTest() {
        when(categoryRepository.existsById( CATEGORY_PARENT.getId())).thenReturn(true);
        when(categoryRepository.save(CATEGORY)).thenReturn(CATEGORY);
        when(categoryDtoMapper.mapToDto(CATEGORY)).thenReturn(CATEGORY_DTO);
        when(categoryDtoMapper.mapToModel(CATEGORY_DTO)).thenReturn(CATEGORY);
        assertThat(categoryService.addCategory(CATEGORY_DTO),is(equalTo(CATEGORY_DTO)));
    }

    @Test
    void addCategory_ParentNotExistsTest() {
        when(categoryRepository.existsById( CATEGORY_PARENT.getId())).thenReturn(false);
        assertThrows(CategoryNotFoundException.class,()->categoryService.addCategory(CATEGORY_DTO));
    }

    @Test
    void updateCategoryTest() {
        when(categoryRepository.save(CATEGORY)).thenReturn(CATEGORY);
        when(categoryRepository.existsById( anyLong())).thenReturn(true);
        when(categoryDtoMapper.mapToDto(CATEGORY)).thenReturn(CATEGORY_DTO);
        when(categoryDtoMapper.mapToModel(CATEGORY_DTO)).thenReturn(CATEGORY);
        assertThat(categoryService.updateCategory(CATEGORY_ID,CATEGORY_DTO),is(equalTo(CATEGORY_DTO)));
    }

    @Test
    void updateCategory_SelfReferencingTest() {
        when(categoryRepository.existsById( anyLong())).thenReturn(true);
        assertThrows(SelfReferencingException.class,()->categoryService.updateCategory(CATEGORY_PARENT.getId(),CATEGORY_DTO));
    }
    @Test
    void updateCategory_NotExistTest() {
        when(categoryRepository.existsById( CATEGORY_ID)).thenReturn(false);
        assertThrows(CategoryNotFoundException.class,()->categoryService.updateCategory(CATEGORY_ID,CATEGORY_DTO));
    }

    @Test
    void updateCategory_ParentNotExistTest() {
        when(categoryRepository.existsById( CATEGORY_ID)).thenReturn(true);
        when(categoryRepository.existsById( CATEGORY_PARENT.getId())).thenReturn(false);
        assertThrows(CategoryNotFoundException.class,()->categoryService.updateCategory(CATEGORY_ID,CATEGORY_DTO));
    }

    @Test
    void deleteCategoryTest() {
        when(categoryRepository.existsById( CATEGORY_ID)).thenReturn(true);
        categoryService.deleteCategory(CATEGORY_ID);
        verify(categoryRepository).deleteById(CATEGORY_ID);
    }

    @Test
    void deleteCategory_NotExistsTest() {
        when(categoryRepository.existsById( CATEGORY_ID)).thenReturn(false);
        assertThrows(CategoryNotFoundException.class,()->categoryService.deleteCategory(CATEGORY_ID));
    }

    @Test
    void getSubCategoriesTest() {
        when(categoryRepository.existsById( CATEGORY_PARENT.getId())).thenReturn(true);
        when(categoryRepository.getSubcategories(CATEGORY_PARENT.getId())).thenReturn(List.of(CATEGORY));
        when(categoryDtoMapper.mapToDto(CATEGORY)).thenReturn(CATEGORY_DTO);
        assertThat(categoryService.getSubcategories(CATEGORY_PARENT.getId()),contains(CATEGORY_DTO));
    }

    @Test
    void getSubCategories_NotExistsTest() {
        when(categoryRepository.existsById(anyLong())).thenReturn(false);
        assertThrows(CategoryNotFoundException.class,()->categoryService.getSubcategories(CATEGORY_PARENT.getId()));
    }

    @Test
    void getRootCategoriesTest() {
        when(categoryDtoMapper.mapToDto(CATEGORY)).thenReturn(CATEGORY_DTO);
        when(categoryRepository.getRootCategories()).thenReturn(List.of(CATEGORY));
        assertThat(categoryService.getRootCategories(),contains(CATEGORY_DTO));
    }

    @Test
    void existsById_ExistsTest() {
       when(categoryRepository.existsById(CATEGORY_ID)).thenReturn(true);
       assertTrue(categoryService.existsById(CATEGORY_ID));
    }

    @Test
    void existsById_NotExistsTest() {
        when(categoryRepository.existsById(CATEGORY_ID)).thenReturn(false);
        assertFalse(categoryService.existsById(CATEGORY_ID));
    }
}