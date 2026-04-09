package com.example.securityApp.SpringSecurity.services;

import com.example.securityApp.SpringSecurity.Exception.ResourceNotFoundException;
import com.example.securityApp.SpringSecurity.dto.SignUpDto;
import com.example.securityApp.SpringSecurity.dto.UserDto;
import com.example.securityApp.SpringSecurity.entities.Users;
import com.example.securityApp.SpringSecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(()->new ResourceNotFoundException("The user with " +username+" not found"));
    }

    public Users getUserByID(Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("The user with " +id+" not found"));
    }

    public UserDto signUpUser(SignUpDto signUpDto) {

        Optional<Users> user = userRepository.findByEmail(signUpDto.getEmail());

        if(user.isPresent()){
            throw new BadCredentialsException("User already present!!");
        }

        Users userCredential = modelMapper.map(signUpDto,Users.class);
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        //here we are encoding password before storing it in DB.

        Users savedUser = userRepository.save(userCredential);
        return modelMapper.map(savedUser,UserDto.class);
    }
}
