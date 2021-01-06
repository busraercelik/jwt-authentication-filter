package jwt.auth.controllers;

import jwt.auth.dto.UserCredentialDto;
import jwt.auth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static jwt.auth.security.constants.SecurityConstant.SIGN_UP_URL;


@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(name = "/hello")
    public ResponseEntity<?> hello(){
        String username= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("User "+username+" is running the request!");
        System.out.println("test a controller method");
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }

    @PostMapping(name = SIGN_UP_URL)
    public ResponseEntity<?> createNewUser(@RequestBody UserCredentialDto credentialDto){
        System.out.println("osoagduadiagu");
        return new ResponseEntity<>(userService.registerNewUserAccount(credentialDto), HttpStatus.OK);
    }
}
