package my.flick.rd.springproject.service.impl;

import lombok.RequiredArgsConstructor;
import my.flick.rd.springproject.annotation.RequireAdminPrivileges;
import my.flick.rd.springproject.dto.CategoryDto;
import my.flick.rd.springproject.exception.CategoryNotFoundException;
import my.flick.rd.springproject.exception.SelfReferencingException;
import my.flick.rd.springproject.model.Category;
import my.flick.rd.springproject.repository.CategoryRepository;
import my.flick.rd.springproject.service.CategoryService;
import my.flick.rd.springproject.util.mapper.CategoryDtoMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryDtoMapper categoryDtoMapper;

    @Override
    public CategoryDto getCategoryById(long id) {
        return categoryRepository
                .findById(id)
                .map(categoryDtoMapper::mapToDto)
                .orElseThrow(() -> new CategoryNotFoundException("Category with id specified does not exist"));
    }


    @Override
    @Transactional
    @RequireAdminPrivileges
    public CategoryDto addCategory(CategoryDto categoryDto) {
        if (!parentExist(categoryDto)) {
            throw new CategoryNotFoundException("Parent with id specified does not exist");
        }
        Category category = categoryRepository.save(categoryDtoMapper.mapToModel(categoryDto));
        return categoryDtoMapper.mapToDto(category);
    }

    @Override
    @Transactional
    @RequireAdminPrivileges
    public CategoryDto updateCategory(long id, CategoryDto categoryDto) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("Category with id specified does not exist");
        }
        if (!parentExist(categoryDto)) {
            throw new CategoryNotFoundException("Category parent does not exist");
        }
        if(id==categoryDto.getParentId()){
            throw new SelfReferencingException("Category`s id and parentId must be different");
        }
        Category category = categoryDtoMapper.mapToModel(categoryDto);
        category.setId(id);
        category = categoryRepository.save(category);
        return categoryDtoMapper.mapToDto(category);
    }


    private boolean parentExist(CategoryDto categoryDto) {
        return categoryDto.getParentId() == 0 || categoryRepository.existsById(categoryDto.getParentId());
    }

    @Override
    @Transactional
    @RequireAdminPrivileges
    public void deleteCategory(long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("Category with id specified does not exists");
        }
        categoryRepository.deleteById(id);
    }

    public List<CategoryDto> getSubcategories(long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("Category with id specified does not exists");
        }
        return categoryRepository.getSubcategories(id).stream()
                .map(categoryDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> getRootCategories() {
        return categoryRepository.getRootCategories().stream()
                .map(categoryDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public boolean existsById(long id){
        return categoryRepository.existsById(id);
    }
}
