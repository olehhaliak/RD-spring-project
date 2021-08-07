package my.flick.rd.springproject.util.dtomapper.impl;

import my.flick.rd.springproject.dto.CategoryDto;
import my.flick.rd.springproject.model.Category;
import my.flick.rd.springproject.util.dtomapper.CategoryDtoMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryDtoMapperImpl implements CategoryDtoMapper {
    @Override
    public Category mapToModel(CategoryDto dto) {
        return Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .parent(getParent(dto))
                .build();
    }

    @Override
    public CategoryDto mapToDto(Category model) {
        return CategoryDto.builder()
                .id(model.getId())
                .name(model.getName())
                .parentId(model.getParent().getId())
                .build();
    }

    private Category getParent(CategoryDto dto) {
        return Category.builder()
                .id(dto.getParentId())
                .build();
    }

}
