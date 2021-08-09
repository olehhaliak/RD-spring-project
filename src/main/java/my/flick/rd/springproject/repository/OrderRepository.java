package my.flick.rd.springproject.repository;

import my.flick.rd.springproject.model.Order;
import my.flick.rd.springproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select r from Receipt r where r.customer.id = :customerId")
    List<Order> findOrderByCustomer(long customerId);
}
