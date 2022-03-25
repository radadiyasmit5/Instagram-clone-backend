package com.example.demo.service.impl.impl;


import com.example.demo.Exception.UsernotfoundException;
import com.example.demo.Security.CustomUserDetailsService;
import com.example.demo.entity.Roles;
import com.example.demo.entity.User;
import com.example.demo.payload.JwttokenResponse;
import com.example.demo.payload.LoginDto;
import com.example.demo.payload.SignupDto;
import com.example.demo.payload.SignupResponseDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.AuthenticationAndRegisterService;
import com.example.demo.utils.Jwtutilsproviders;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationAndRegisterServiceimpl implements AuthenticationAndRegisterService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private Jwtutilsproviders jwtutilsproviders;

    /*** register user ***/
    @Override
    public SignupResponseDTO userSignup(SignupDto signupDto) {


        User user = mapToEntity(signupDto);

        try {
            User registeredUser = userRepository.save(user);
            SignupResponseDTO signupResponseDTO = new SignupResponseDTO();
            signupResponseDTO.setEmail(registeredUser.getEmail());
            signupResponseDTO.setUsername(registeredUser.getUsername());
            signupResponseDTO.setFullname(registeredUser.getFullname());
            return signupResponseDTO;
        } catch (Exception e) {
            throw new UsernotfoundException("There is some problem while creating user with username " + signupDto.getUsername() + " and emailorphone " + signupDto.getEmail() + signupDto.getPhone());
        }


    }


    /*** checking if user exist by username or phone or email ***/

    @Override
    public boolean existbyusernameoremailorphone(String usernameoremailorphone) {
        return userRepository.existsByUsernameOrEmailOrPhone(usernameoremailorphone, usernameoremailorphone, usernameoremailorphone).orElseThrow(() -> new UsernotfoundException("user not found with username or email or phone " + usernameoremailorphone));
    }


    /*** login user ***/
    @Override
    public JwttokenResponse userLogin(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

        } catch (BadCredentialsException e) {
            throw new UsernotfoundException("Sorry, your password was incorrect. Please double-check your password. ");
        } catch (Exception e) {
//            throw new Exception(e.getMessage());
            if (e.getMessage() == "user not found") {
                throw new UsernotfoundException("The username you entered doesn't belong to an account. Please check your username and try again.");

            } else {
                throw new UsernotfoundException("there is some problem while authenticating user info please try to enter your username and password again");
            }
        }

        UserDetails userdetails = customUserDetailsService.loadUserByUsername(loginDto.getUsername());
        String token = jwtutilsproviders.generateToken(userdetails);
        JwttokenResponse jwttokenResponse = new JwttokenResponse();
        jwttokenResponse.setToken(token);
        jwttokenResponse.setUsername(userdetails.getUsername());
        return jwttokenResponse;
    }

    private User mapToEntity(SignupDto signupDto) {
        ModelMapper modelMapper = new ModelMapper();

        User user = modelMapper.map(signupDto, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(signupDto.getPassword()));
        Set set = new HashSet();
        set.add(new Roles("ROLE_USER"));
        user.setRoles(set);

        return user;
    }

    private SignupDto maptoDto(User user) {
        ModelMapper modelMapper = new ModelMapper();
        SignupDto signupDto = modelMapper.map(user, SignupDto.class);
        return signupDto;

    }
}
