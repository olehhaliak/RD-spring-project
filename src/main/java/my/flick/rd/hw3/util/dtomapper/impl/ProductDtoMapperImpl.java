package my.flick.rd.hw3.util.dtomapper.impl;

import com.zaxxer.hikari.util.PropertyElf;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.flick.rd.hw3.dto.ProductDto;
import my.flick.rd.hw3.model.Product;
import my.flick.rd.hw3.util.dtomapper.DtoMapper;
import my.flick.rd.hw3.util.dtomapper.ProductDtoMapper;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductDtoMapperImpl implements ProductDtoMapper {
    private final PropertyUtilsBean propertyUtils;

    @Override
    public Product mapToModel(ProductDto dto) {
        Product product = new Product();
        try {
            propertyUtils.copyProperties(product, dto);
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
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("Failed to map Product to ProductDto", e);
        }
        return dto;
    }
}
