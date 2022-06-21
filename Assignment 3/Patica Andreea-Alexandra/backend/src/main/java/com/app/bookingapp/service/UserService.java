package com.app.bookingapp.service;

import com.app.bookingapp.data.dto.mapper.UserMapper;
import com.app.bookingapp.data.dto.model.UserDto;
import com.app.bookingapp.data.sql.entity.*;
import com.app.bookingapp.data.sql.repo.AccountTypeRepository;
import com.app.bookingapp.data.sql.repo.RoleRepository;
import com.app.bookingapp.data.sql.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final AccountTypeRepository accountTypeRepository;
    private final RoleRepository roleRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    //TODO all regular users

    public UserDto findById(Long id){
        return userRepository.findById(id)
                .map(userMapper::userToUserDto)
                .orElseThrow(() -> new RuntimeException(format("Card with id %s not found", id)));
    }

    public UserDto create(UserDto userDto){
        Optional<AccountType> accountTypeOp = accountTypeRepository.findByName(userDto.getAccountType().getName());
        Optional<Role> roleOp = roleRepository.findByName(userDto.getRole().getName());

        User user = userMapper.userDtoToUser(userDto);

        accountTypeOp.ifPresent(user::setAccountType);
        roleOp.ifPresent(user::setRole);

        UserDto userDtoAdded;

        try{
            userDtoAdded = userMapper.userToUserDto(userRepository.save(user));
        }catch(Exception e){
            userDtoAdded = null;
        }

        return userDtoAdded;

    }

    public UserDto update(Long id, UserDto userDto){
        User userEntity = userMapper.userDtoToUser(userDto);
        userEntity.setId(id);

        Optional<AccountType> accountTypeOp = accountTypeRepository.findByName(userDto.getAccountType().getName());
        Optional<Role> roleOp = roleRepository.findByName(userDto.getRole().getName());

        User user = userMapper.userDtoToUser(userDto);

        accountTypeOp.ifPresent(user::setAccountType);
        roleOp.ifPresent(user::setRole);

        UserDto userDtoAdded;

        try{
            userDtoAdded = userMapper.userToUserDto(userRepository.save(user));
        }catch(Exception e){
            userDtoAdded = null;
        }

        return userDtoAdded;
    }

    public void delete(String username){
        userRepository.deleteByUsername(username);
    }
}
