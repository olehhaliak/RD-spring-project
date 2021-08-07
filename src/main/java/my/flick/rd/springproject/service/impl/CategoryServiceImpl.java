package my.flick.rd.springproject.service.impl;

import lombok.RequiredArgsConstructor;
import my.flick.rd.springproject.dto.CategoryDto;
import my.flick.rd.springproject.exception.CategoryNotFoundException;
import my.flick.rd.springproject.model.Category;
import my.flick.rd.springproject.repository.CategoryRepository;
import my.flick.rd.springproject.service.CategoryService;
import my.flick.rd.springproject.util.dtomapper.CategoryDtoMapper;
import org.springframework.stereotype.Service;

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
    public CategoryDto addCategory(CategoryDto categoryDto) {
        if (!parentExist(categoryDto)) {
            throw new CategoryNotFoundException("Category with id specified does not exist");
        }
        Category category = categoryRepository.save(categoryDtoMapper.mapToModel(categoryDto));
        return categoryDtoMapper.mapToDto(category);
    }

    @Override
    public CategoryDto updateCategory(long id, CategoryDto categoryDto) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("Category with id specified does not exist");
        }
        if (!parentExist(categoryDto)) {
            throw new CategoryNotFoundException("Category parent does not exist");
        }
        Category category = categoryDtoMapper.mapToModel(categoryDto);
        category = categoryRepository.save(category);
        return categoryDtoMapper.mapToDto(category);
    }


    private boolean parentExist(CategoryDto categoryDto) {
        return categoryDto.getParentId() == 0 || categoryRepository.existsById(categoryDto.getParentId());
    }

    @Override
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


}
