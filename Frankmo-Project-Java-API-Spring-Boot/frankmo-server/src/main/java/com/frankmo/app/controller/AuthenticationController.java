package com.frankmo.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.frankmo.app.usermanagement.model.LoginResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.frankmo.app.usermanagement.model.dao.UserDao;
import com.frankmo.app.usermanagement.model.LoginDto;
import com.frankmo.app.usermanagement.model.RegisterUserDto;
import com.frankmo.app.usermanagement.model.User;
import com.frankmo.app.javasecurity.jwt.TokenProvider;
import org.springframework.web.server.ResponseStatusException;

import static com.frankmo.generalpurposeutilities.LogHttpRequest.logHttpRequest;

/**
 * Controller to authenticate users.
 */
@RestController
public class AuthenticationController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserDao userDao;

    public AuthenticationController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserDao userDao) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDao = userDao;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public LoginResponseDto login(HttpServletRequest theHttpRequest,       // Get request as an HttpServletRequest object
                                  @Valid @RequestBody LoginDto loginDto) { // Get JSON from request bodY and create LoginDto object

        logHttpRequest(theHttpRequest);    // Pass the request info to the logging method

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, false);
        
        User user = userDao.findByUsername(loginDto.getUsername());

        return new LoginResponseDto(jwt, user);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(HttpServletRequest theHttpRequest,             // Get request as an HttpServletRequest object
                         @Valid @RequestBody RegisterUserDto newUser) { // Get JSON from request bodY and create RegisterUserDto object

        logHttpRequest(theHttpRequest);    // Pass the request info to the logging method
        //LogAPIRequest.logAPICall("POST for path /register for username: " + newUser.getUsername());

        if (!userDao.create(newUser.getUsername(), newUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User registration failed.");
        }
    }

}

