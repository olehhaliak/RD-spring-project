package my.flick.rd.springproject.repository;

import my.flick.rd.springproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
