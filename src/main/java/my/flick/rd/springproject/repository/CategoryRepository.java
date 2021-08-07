package my.flick.rd.springproject.repository;

import my.flick.rd.springproject.model.Category;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query("SELECT c FROM Category c WHERE c.parent = ?1")
    List<Category> getSubcategories(long id);

}
