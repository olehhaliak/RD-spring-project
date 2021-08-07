package my.flick.rd.springproject.repository;

import my.flick.rd.springproject.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query("SELECT c FROM Category c WHERE c.parent.id = ?1")
    List<Category> getSubcategories(long id);

    @Query("SELECT c FROM Category c WHERE c.parent.id = NULL")
    List<Category> getRootCategories();
}
