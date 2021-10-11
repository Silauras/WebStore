package edu.sytoss.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SignInController {
    @GetMapping( "/login")
    public ResponseEntity login(){
        return new ResponseEntity(HttpStatus.OK);
    }
}
