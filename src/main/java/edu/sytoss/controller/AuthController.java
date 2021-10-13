package edu.sytoss.controller;

import edu.sytoss.dto.CustomerSignUpDTO;
import edu.sytoss.dto.LoginCheckDTO;
import edu.sytoss.dto.LoginDTO;
import edu.sytoss.model.user.UserAccount;
import edu.sytoss.model.user.UserAccountRole;
import edu.sytoss.service.UserAccountAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserAccountAPI userAccountAPI;

    public AuthController(UserAccountAPI userAccountAPI) {
        this.userAccountAPI = userAccountAPI;
    }

    @GetMapping("/usernameIsFree")
    public Boolean usernameIsFree(String username) {
        System.out.println("AuthController.usernameIsFree():");
        System.out.println(username);
        //TODO: check if the given username is busy,
        // return true if it does not exist in the database
        // example: "admin" will return false
        return true;
    }

    @PostMapping("/signUpCustomer")
    public Boolean registerCustomerUserAccount(@RequestBody CustomerSignUpDTO customerSignUpDTO) {
        System.out.println("AuthController.registerCustomerUserAccount():");
        System.out.println(customerSignUpDTO.toString());

        // TODO: registration of new customer
        // UserAccount api is already imported
        /*UserAccount newCustomer = new UserAccount(
                customerSignUpDTO.getSurname(),
                customerSignUpDTO.getName(),
                customerSignUpDTO.getUsername(),
                customerSignUpDTO.getPassword(),
                new Date(System.currentTimeMillis()), // registration date
                new Date(System.currentTimeMillis()), // last activity date
                UserAccountRole.CUSTOMER.name()
        );
        try {
            userAccountAPI.createUserAccount(newCustomer);
        } catch (Exception e) {
            return false;
        }*/
        return true;
    }

    @GetMapping("/login")
    public Long loginAccount(LoginDTO loginDTO) {
        System.out.println("AuthController.loginAccount():");
        System.out.println(loginDTO.toString());
        // TODO: return user account id for login and password
        return 1L;
    }
}
