package by.itacademy.railway.repository;

import by.itacademy.railway.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
    Optional<User> login(String email, String password);

    Page<User> findAll(Pageable pageable);

    Optional<User> findByEmail(String email);

}
