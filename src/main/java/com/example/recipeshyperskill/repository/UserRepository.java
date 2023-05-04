package com.example.recipeshyperskill.repository;

import com.example.recipeshyperskill.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("select u from User u where upper(u.email) = upper(?1)")
    Optional<User> findByEmailIgnoreCase(@NonNull String email);
}
