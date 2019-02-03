package bdi.azd.gedi.data.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import bdi.azd.gedi.data.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

  List<User> findAll();

  Optional<User> findByUsername(String username);

}
