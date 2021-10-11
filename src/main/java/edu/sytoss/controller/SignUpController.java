package edu.sytoss.controller;

import edu.sytoss.dto.CustomerSignUpDTO;
import edu.sytoss.model.user.UserAccount;
import edu.sytoss.model.user.UserAccountRole;
import edu.sytoss.service.UserAccountAPI;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class SignUpController {

    //@GetMapping("/signup")
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signUpPage() {
        System.out.println("signup controller");
        return "signup";
    }

//    @PostMapping("/signup")
//    public String attemptRegistration(@ModelAttribute("signUpDTO") CustomerSignUpDTO signUpDTO,
//                                      BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "signup";
//        }
//
//        System.out.println("signup controller post");
//        System.out.println(signUpDTO);
//        UserAccount customer = new UserAccount(
//                signUpDTO.getSurname(),
//                signUpDTO.getName(),
//                signUpDTO.getUsername(),
//                signUpDTO.getPassword(),
//                new Date(System.currentTimeMillis()),
//                new Date(System.currentTimeMillis()),
//                UserAccountRole.CUSTOMER.name());
//        if (userAccountAPI.createUserAccount(customer))
//            return "redirect:/login";
//        else {
//            return "signup";
//        }
//    }
}
