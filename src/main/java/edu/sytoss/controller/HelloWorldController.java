package edu.sytoss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloWorldController {

    @RequestMapping(value = "/go", method = RequestMethod.GET)
    public String test() {
        System.out.println("test controller");
        return "g";
    }
}
