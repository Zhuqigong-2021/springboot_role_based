package com.auth.management.service;

import com.auth.management.dto.RegistrationDto;
import com.auth.management.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    void saveNurse(RegistrationDto registrationDto);
    void saveDoctor(RegistrationDto registrationDto);

    //use for both patient and doctor
    void saveUpdate(User user);





    User findByEmail(String email);

    List<User> findByRole(String role);

    User findDoctorById(Long id);

    User findPatientById(Long id);

    User findUserById(Long id);

    void deleteUserById(Long id);

    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);

    User findByPhoneNumber(String phoneNumber);

    List<User> findAll();

    Page<User> findPaginated(int number, int size);

    Page<User> search(String keyword, Pageable pageable);
}
