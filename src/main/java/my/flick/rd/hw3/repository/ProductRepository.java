package my.flick.rd.hw3.repository;

import my.flick.rd.hw3.entity.Product;
import org.springframework.data.repository.CrudRepository;

public abstract class ProductRepository implements CrudRepository<Product,Long> {
}
