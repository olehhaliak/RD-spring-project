package my.flick.rd.hw3.service;

import lombok.AllArgsConstructor;
import my.flick.rd.hw3.dto.ProductRequestDto;
import my.flick.rd.hw3.entity.Product;
import my.flick.rd.hw3.repository.ProductRepository;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
   private ProductRepository productRepository;
   private PropertyUtilsBean propertyUtils;

    public List<Product> getAllProducts() {
       return (List<Product>) productRepository.findAll();
    }

    public long addProduct(ProductRequestDto productRequestDto) {
        Product product = new Product();
        try {
            propertyUtils.copyProperties(product,productRequestDto);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return productRepository.save(product).getId();
    }

    public Optional<Product> getProduct(long id) {
        return productRepository.findById(id);
    }

    /**
     * Updates product passed as an argument
     * @param productRequestDto
     * @return true if successfully updated, else returns false
     */
    public boolean updateProductById(ProductRequestDto productRequestDto,long id){

        if(productRepository.existsById(id)){
            Product product = new Product();
            try {
                propertyUtils.copyProperties(product,productRequestDto);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            product.setId(id);
            productRepository.save(product);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Deletes product passed as an argument
     * @param id
     * @return true if successfully deleted, else returns false
     */
    public boolean deleteProduct(long id) {
        if(productRepository.existsById(id)){
            productRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }
}
