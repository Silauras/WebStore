package edu.sytoss.security.rest;

import edu.sytoss.model.user.UserAccount;
import edu.sytoss.repository.UserAccountRepository;
import edu.sytoss.security.JwtTokenProvider;
import edu.sytoss.service.UserAccountAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationRestControllerV1  {

    private final AuthenticationManager authenticationManager;
    private final UserAccountRepository userAccountRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, UserAccountRepository userAccountRepository, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userAccountRepository = userAccountRepository;
        this.jwtTokenProvider = jwtTokenProvider;

    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO request) {
        try {
            System.out.println("AuthenticationRestControllerV1.authenticate() "+request);
            System.out.println("AuthenticationRestControllerV1.authenticate() " + new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));

            UserAccount user = userAccountRepository.findByLogin(request.getLogin());
            String token = jwtTokenProvider.createToken(request.getLogin(), user.getRole());
            Map<Object, Object> response = new HashMap<>();
            response.put("login", request.getLogin());
            response.put("token", token);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization","Bearer "+token);
            return new ResponseEntity("",httpHeaders,HttpStatus.OK);
        } catch (AuthenticationException e) {
            System.out.println("AuthenticationRestControllerV1.authenticate() CATCH");
            return new ResponseEntity<>("Invalid login/password combination", HttpStatus.FORBIDDEN);
        }
    }
    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
