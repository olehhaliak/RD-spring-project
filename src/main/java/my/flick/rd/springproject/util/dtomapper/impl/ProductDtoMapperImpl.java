package my.flick.rd.springproject.util.dtomapper.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.flick.rd.springproject.dto.ProductDto;
import my.flick.rd.springproject.model.Category;
import my.flick.rd.springproject.model.Product;
import my.flick.rd.springproject.util.dtomapper.ProductDtoMapper;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component
@Slf4j
public class ProductDtoMapperImpl implements ProductDtoMapper {
    private final PropertyUtilsBean propertyUtils = new PropertyUtilsBean();

    @Override
    public Product mapToModel(ProductDto dto) {
        Product product = new Product();

        try {
            propertyUtils.copyProperties(product, dto);
            product.setCategory(Category.builder().id(dto.getCategoryId()).build());
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("Failed to map ProductDto to Product", e);
        }
        return product;
    }

    @Override
    public ProductDto mapToDto(Product model) {
        ProductDto dto = new ProductDto();
        try {
            propertyUtils.copyProperties(dto, model);
            dto.setCategoryId(model.getCategory().getId());
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("Failed to map Product to ProductDto", e);
        }
        return dto;
    }
}
