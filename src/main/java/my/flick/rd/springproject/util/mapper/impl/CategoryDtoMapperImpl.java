package my.flick.rd.springproject.util.mapper.impl;

import my.flick.rd.springproject.dto.CategoryDto;
import my.flick.rd.springproject.model.Category;
import my.flick.rd.springproject.util.mapper.CategoryDtoMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
                .parentId(getParentId(model))
                .build();
    }

    private long getParentId(Category model) {
        if (Objects.isNull(model.getParent())) {
            return 0;
        } else {
            return model.getParent().getId();
        }
    }

    private Category getParent(CategoryDto dto) {
        if (dto.getParentId() != 0) {
            return Category.builder().id(dto.getParentId()).build();
        } else {
            return null;
        }
    }

}
