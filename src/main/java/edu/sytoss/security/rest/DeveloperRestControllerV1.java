package edu.sytoss.security.rest;

import edu.sytoss.model.user.UserAccount;
import edu.sytoss.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperRestControllerV1 {
    @Autowired
    UserAccountRepository userAccountRepository;

    @GetMapping
    public String getAll() {
        System.out.println("eeee");
        System.out.println(userAccountRepository.findAll());
        return userAccountRepository.findAll().toString();
    }

    @GetMapping("/{id}")
   // @PreAuthorize("hasAuthority('userAccount:read')")
    public String getById(@PathVariable Long id) {
        System.out.println("eee");
        return userAccountRepository.findById(id).toString();
    }
//
//    @PostMapping
//    @PreAuthorize("hasAuthority('developers:write')")
//    public Developer create(@RequestBody Developer developer) {
//        this.DEVELOPERS.add(developer);
//        return developer;
//    }
//
//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('developers:write')")
//    public void deleteById(@PathVariable Long id) {
//        this.DEVELOPERS.removeIf(developer -> developer.getId().equals(id));
//    }
}
