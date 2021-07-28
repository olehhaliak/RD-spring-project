package my.flick.rd.hw3.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import my.flick.rd.hw3.dto.ProductRequestDto;
import my.flick.rd.hw3.entity.Product;
import my.flick.rd.hw3.repository.ProductRepository;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
   private ProductRepository productRepository;


    public List<Product> getAllProducts() {
       return (List<Product>) productRepository.findAll();
    }

    public long addProduct(ProductRequestDto productRequestDto) {
        Product product = new Product();
        try {
            new PropertyUtilsBean().copyProperties(product,productRequestDto);
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
     * @param product
     * @return true if successfully updated, else returns false
     */
    public boolean updateProduct(Product product){
        if(productRepository.existsById(product.getId())){
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
