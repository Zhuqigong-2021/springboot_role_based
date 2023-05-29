package com.auth.management.repository;


import com.auth.management.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
    List<User> findByRole(@Param("roleName") String roleName);

    List<User> findByFirstName(String firstName);
    List<User> findByLastName(String lastName);

    User findByPhoneNumber(String phoneNumber);


    @Query("SELECT u FROM User u")
    List<User> findAll();



    @Query("SELECT u FROM User u WHERE " +
            "LOWER(u.email) LIKE LOWER(concat('%', :keyword, '%')) OR " +
            "LOWER(u.phoneNumber) LIKE LOWER(concat('%', :keyword, '%')) OR " +
            "LOWER(u.firstName) LIKE LOWER(concat('%', :keyword, '%')) OR " +
            "LOWER(u.lastName) LIKE LOWER(concat('%', :keyword, '%'))")
    Page<User> findByKeyword(String keyword, Pageable pageable);
}