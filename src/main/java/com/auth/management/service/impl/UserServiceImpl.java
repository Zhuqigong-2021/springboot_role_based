package com.auth.management.service.impl;

import com.auth.management.dto.RegistrationDto;
import com.auth.management.entity.Role;
import com.auth.management.entity.User;
import com.auth.management.mapper.MapperHelper;
import com.auth.management.model.UserModel;
import com.auth.management.repository.RoleRepository;
import com.auth.management.repository.UserRepository;
import com.auth.management.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final MapperHelper mapperHelper;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, MapperHelper mapperHelper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapperHelper = mapperHelper;
    }

    @Override
    public void saveUser(RegistrationDto registrationDto) {
        User user = new User();
//        user.setName(registrationDto.getFirstName() + " " + registrationDto.getLastName());
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setEmail(registrationDto.getEmail());
        // use spring security to encrypt the password
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setPhoneNumber(registrationDto.getPhoneNumber());
        Role role = roleRepository.findByName("ROLE_GUEST");

        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }
    @Override
    public void saveNurse(RegistrationDto registrationDto) {
        User user = new User();
//        user.setName(registrationDto.getFirstName() + " " + registrationDto.getLastName());
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setEmail(registrationDto.getEmail());
        // use spring security to encrypt the password
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setPhoneNumber(registrationDto.getPhoneNumber());
        Role role = roleRepository.findByName("ROLE_MANAGER");
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public void saveDoctor(RegistrationDto registrationDto) {
        User user = new User();
//        user.setName(registrationDto.getFirstName() + " " + registrationDto.getLastName());
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setEmail(registrationDto.getEmail());
        // use spring security to encrypt the password
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setPhoneNumber(registrationDto.getPhoneNumber());
        Role role = roleRepository.findByName("ROLE_DOCTOR");
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public void saveUpdate(User user) {
        userRepository.save(user);
    }


    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findByFirstName(String firstName) {
        return  userRepository.findByFirstName(firstName);
    }

    @Override
    public List<User> findByLastName(String lastName) {
        return userRepository.findByLastName(lastName);
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }


    @Override
    public User findDoctorById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        User user = null;
          if(userOptional.isPresent()){
              user =userOptional.get();
          }else{
              throw new RuntimeException("do not find the doctor id" + id);
          }
          return user;
//        return user.map(mapperHelper::convertUserEntityToRegisterDto).orElse(null);

    }

    @Override
    public User findPatientById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        User user = null;
        if(userOptional.isPresent()){
            user =userOptional.get();
        }else{
            throw new RuntimeException("do not find the patient id" + id);
        }
        return user;
    }

    @Override
    public User findUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        User user = null;
        if(userOptional.isPresent()){
            user =userOptional.get();
        }else{
            throw new RuntimeException("do not find the user id" + id);
        }
        return user;
    }


    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findByRole(String role) {
        List<User> users_with_role = userRepository.findByRole(role);

        return users_with_role;
    }
}