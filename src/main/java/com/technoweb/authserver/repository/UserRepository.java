package com.technoweb.authserver.repository;

import com.technoweb.authserver.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailOrUsername(String email, String username);
    List<User> findByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.password = :password WHERE u.username = :username")
    int updatePasswordByUsername(@Param("username") String username, @Param("password") String password);
}
