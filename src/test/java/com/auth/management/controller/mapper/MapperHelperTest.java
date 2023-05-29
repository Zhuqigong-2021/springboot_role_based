package com.auth.management.controller.mapper;

import com.auth.management.dto.RegistrationDto;
import com.auth.management.entity.User;
import com.auth.management.mapper.MapperHelper;
import com.auth.management.model.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class MapperHelperTest {
    static RegistrationDto registrationDto;
    static User user;
    static UserModel userModel;

    static MapperHelper mapperHelper;


    @BeforeAll
    static void beforeAll() {
        registrationDto = new RegistrationDto(
                1L, "test first name", "test last name", "test email", "test pass", "test phone number"
        );
        user = new User(1L, "test first name", "test last name", "test email", "test pass", "test phone number", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        userModel = new UserModel(1L, "test first name", "test last name", "test email", "test pass", "test phone number");
        mapperHelper = new MapperHelper(new ObjectMapper());
    }

    @Test
    void convertUserModelToRegisterDto() {
        RegistrationDto convertedRegistrationDto = mapperHelper.convertUserModelToRegisterDto(userModel);
        assertThat(convertedRegistrationDto).usingRecursiveComparison().isEqualTo(registrationDto);
    }

    @Test
    void convertRegisterDto() {
        UserModel convertedUserModel = mapperHelper.convertRegisterDto(registrationDto);
        assertThat(convertedUserModel).usingRecursiveComparison().isEqualTo(userModel);
    }

}