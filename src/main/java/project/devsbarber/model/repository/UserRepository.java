package project.devsbarber.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.devsbarber.model.entities.User;

import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByUsername(String username);

    User getByUsername(String username);

    User getById(Long id);

    @Query(value = "SELECT u FROM User u order by u.id")
    Page<User> findAllUsersWhitPagination(Pageable pageable);

    @Query(value = "SELECT u FROM User u where u.id = :id and u.password = :password")
    User validatePassword(@Param("id") Long userId,@Param("password") String password);
}