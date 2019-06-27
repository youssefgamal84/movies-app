package com.joe.auth;

import com.joe.auth.security.TokenUtil;
import com.joe.auth.user.LoginParams;
import com.joe.auth.user.User;
import com.joe.auth.user.UserAlreadyRegisteredException;
import com.joe.auth.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticator;

    @Autowired
    private TokenUtil tokenUtil;


    @PostMapping("/sign-up")
    public void saveUser(@Valid @RequestBody User user) throws UserAlreadyRegisteredException {
        if (userService.findById(user.getEmail()).isPresent()) {
            throw new UserAlreadyRegisteredException("this email is already in use!");
        }
        userService.saveUser(user);
    }

    @PostMapping("/log-in")
    public HashMap<String, String> loginUser(@Valid @RequestBody LoginParams loginParams) {
        final Authentication authentication = authenticator.authenticate(
                new UsernamePasswordAuthenticationToken(loginParams.getEmail(), loginParams.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userService.loadUserByUsername(loginParams.getEmail());
        String token = tokenUtil.generateToken(userDetails);
        HashMap<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }

    @PostMapping("/identify")
    public String identify(@RequestBody String token) {
        System.out.println(token);
        return this.tokenUtil.getUsername(token);
    }


}
