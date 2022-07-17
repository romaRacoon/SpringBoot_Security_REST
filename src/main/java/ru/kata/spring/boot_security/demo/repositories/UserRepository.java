package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    @Modifying
    @Query(value = "UPDATE users u SET u.username = :username , u.password = :password WHERE u.id = :id",
            nativeQuery = true)
    @Transactional
    int update(@Param("id") Integer id, @Param("password") String password,
                      @Param("username") String username);
}
