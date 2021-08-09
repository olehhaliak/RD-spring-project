package my.flick.rd.springproject.repository;

import my.flick.rd.springproject.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
