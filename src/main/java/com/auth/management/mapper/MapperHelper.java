package com.auth.management.mapper;

import com.auth.management.dto.RegistrationDto;
import com.auth.management.entity.User;
import com.auth.management.model.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapperHelper {
    private final ObjectMapper mapper;

    @Autowired
    public MapperHelper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public List<RegistrationDto> convertUserEntityListToRegisterDtoList(List<User> entities){
        List<RegistrationDto> users = new ArrayList<>();
        for(User entity:entities){
            users.add(mapper.convertValue(entity,RegistrationDto.class));
        }
        return users;
    }


    public RegistrationDto convertUserEntityToRegisterDto(User user){
        return mapper.convertValue(user,RegistrationDto.class);
    }
    public UserModel convertUserEntityToUserModel(User user){
        return mapper.convertValue(user,UserModel.class);
    }
    public User convertUserModelToUserEntity(UserModel userModel){
        return mapper.convertValue(userModel,User.class);
    }
    public RegistrationDto convertUserModelToRegisterDto(UserModel userModel){
        return mapper.convertValue(userModel,RegistrationDto.class);
    }

    public UserModel convertRegisterDto(RegistrationDto registrationDto){
        return mapper.convertValue(registrationDto, UserModel.class);
    }
    public User convertRegisterDtoToUserEntity(RegistrationDto registrationDto){
        return mapper.convertValue(registrationDto,User.class);
    }



}
