package com.auth.management.service.impl;

import com.auth.management.dto.RegistrationDto;
import com.auth.management.entity.Role;
import com.auth.management.entity.User;
import com.auth.management.mapper.MapperHelper;
import com.auth.management.repository.AppointmentRepository;
import com.auth.management.repository.RoleRepository;
import com.auth.management.repository.UserRepository;

import jakarta.inject.Inject;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
   private UserServiceImpl target;
  @Mock
    private UserRepository userRepository;

 @Mock
    private  RoleRepository roleRepository;
 @Mock
    private  PasswordEncoder passwordEncoder;
@Mock
    private AppointmentRepository appointmentRepository;
@Mock
    private  MapperHelper mapperHelper;


//    private UserServiceImpl underTest;
//
//    @BeforeEach
//    void setUp() {
//
//        underTest = new UserServiceImpl(userRepository,roleRepository,passwordEncoder,appointmentRepository,mapperHelper);
//    }

    @Test
    void canGetAllUsers() {
//        List<User> all = underTest.findAll();
//        verify(userRepository).findAll();

        //given
       List<User> expectedUserEntity = new ArrayList<>();

        User user1 =User.builder()
                .id(1L)
                .firstName("test1_firstName")
                .lastName("test1_lastName")
                .password("1234")
                .phoneNumber("5148856354")
                .roles(Arrays.asList(new Role()))
                .build();

        User user2 =User.builder()
                .id(2L)
                .firstName("test2_firstName")
                .lastName("test2_lastName")
                .password("1234")
                .phoneNumber("5148853453")
                .roles(Arrays.asList(new Role()))
                .build();
        User user3 =User.builder()
                .id(3L)
                .firstName("test3_firstName")
                .lastName("test3_lastName")
                .password("1234")
                .phoneNumber("514834525")
                .roles(Arrays.asList(new Role()))
                .build();
       expectedUserEntity.add(user1);
       expectedUserEntity.add(user2);
       expectedUserEntity.add(user3);
        List<RegistrationDto> registrationDtos = new ArrayList<>();
        RegistrationDto registrationDto1 =
                RegistrationDto.builder()
                        .id(1L)
                        .firstName("test1_firstName")
                        .lastName("test1_lastName")
                        .password("1234")
                        .phoneNumber("5148856354")
                        .build();

        //when
        when(userRepository.findAll()).thenReturn(expectedUserEntity);

        //assertion
        List<User> actual =  target.findAll();
        assertNotNull(actual);
        assertEquals(3, actual.size());
        assertEquals(user1, actual.get(0));
        assertEquals(user1.getEmail(), actual.get(0).getEmail());
    }

    @Test

    void canAddOneUser() {
        //given

        RegistrationDto registrationDto =
                RegistrationDto.builder()
                        .id(1L)
                        .firstName("phil")
                        .lastName("ok")
                        .email("phil@gmail.com")
                        .password("1234")
                        .phoneNumber("5148879765")
                        .build();


        //when
        String expectedHash = "hashedPassword";
        when(passwordEncoder.encode(registrationDto.getPassword())).thenReturn(expectedHash);

        //then
          target.saveUser( registrationDto);

        // Verify that the UserRepository's save method is called with the correct User object
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(registrationDto.getLastName(), capturedUser.getLastName());
        assertEquals(expectedHash, capturedUser.getPassword());


    }

    @Test
    void saveUpdate() {
    }

    @Test
    void deleteUserById() {
        target.deleteUserById(1L);
        verify(userRepository).deleteById(1L);
    }


    @Test
    void findByEmail() {
        var expectedUser = User.builder().id(1L)
                .firstName("test1_firstName")
                .lastName("test1_lastName")
                .email("phil@gmail.com")
                .password("1234")
                .phoneNumber("5148856354")
                .roles(Arrays.asList(new Role()))
                .build();


        // Mock the UserRepository's findByEmail method to return the test User object
        when(userRepository.findByEmail(expectedUser.getEmail())).thenReturn(expectedUser);

        // Call the UserService's findByEmail method with the test email address
        User foundUser = target.findByEmail(expectedUser.getEmail());

        // Verify that the UserRepository's findByEmail method was called with the test email address
        verify(userRepository).findByEmail(expectedUser.getEmail());

        // Verify that the correct User object was returned by the UserService's findByEmail method
        assertEquals(expectedUser, foundUser);
        assertEquals("phil@gmail.com",foundUser.getEmail());


    }


}