package my.flick.rd.springproject.repository;

import my.flick.rd.springproject.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {


}
