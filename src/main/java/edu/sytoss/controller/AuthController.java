package edu.sytoss.controller;

import edu.sytoss.dto.CustomerSignUpDTO;
import edu.sytoss.dto.LoginDTO;
import edu.sytoss.mapper.CustomerSingUpDTOMapper;
import edu.sytoss.model.user.UserAccount;
import edu.sytoss.service.UserAccountAPI;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserAccountAPI userAccountAPI;

    private final ModelMapper modelMapper = new ModelMapper();

    public AuthController(UserAccountAPI userAccountAPI) {
        this.userAccountAPI = userAccountAPI;
    }

    @GetMapping("/usernameIsFree")
    public Boolean usernameIsFree(@RequestBody String username) {
        System.out.println("AuthController.usernameIsFree():");
        System.out.println(username);
        //TODO: check if the given username is busy,
        // return true if it does not exist in the database
        // example: "admin" will return false
        for (UserAccount userAccount:userAccountAPI.findUserAccount(new UserAccount(username))) {
            System.out.println(userAccount.getLogin());
            return !userAccount.getLogin().equals(username);
        }
        return true;
    }

    @PostMapping("/signUpCustomer")
    public ResponseEntity<Boolean> registerCustomerUserAccount(@RequestBody CustomerSignUpDTO customerSignUpDTO) {
        System.out.println("AuthController.registerCustomerUserAccount():");
        System.out.println(customerSignUpDTO.toString());
        // TODO: registration of new customer
        CustomerSingUpDTOMapper customerSingUpDTOMapper = new CustomerSingUpDTOMapper();
        UserAccount userAccount = customerSingUpDTOMapper.map(customerSignUpDTO);
        return new ResponseEntity<>(userAccountAPI.createUserAccount(userAccount), HttpStatus.OK);

    }

    @GetMapping("/login")
    public Long loginAccount(LoginDTO loginDTO) {
        System.out.println("AuthController.loginAccount():");
        System.out.println(loginDTO.toString());
        // TODO: return user account id for login and password

        return 1L;
    }
}
