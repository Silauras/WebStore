package edu.sytoss.security.rest;

import edu.sytoss.model.user.UserAccount;
import edu.sytoss.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperRestControllerV1 {
    @Autowired
    UserAccountRepository userAccountRepository;

    @GetMapping
    public List<UserAccount> getAll() {
        System.out.println(userAccountRepository.findAll());
        return userAccountRepository.findAll();
    }

//    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('developers:read')")
//    public Developer getById(@PathVariable Long id) {
//        return DEVELOPERS.stream().filter(developer -> developer.getId().equals(id))
//                .findFirst()
//                .orElse(null);
//    }
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
