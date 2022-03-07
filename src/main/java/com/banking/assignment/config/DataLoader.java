package com.banking.assignment.config;

import com.banking.assignment.persistence.entity.ERole;
import com.banking.assignment.persistence.entity.Role;
import com.banking.assignment.persistence.entity.User;
import com.banking.assignment.persistence.repistory.RoleRepository;
import com.banking.assignment.persistence.repistory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void run(ApplicationArguments args) {
        Role adminRole =new Role();
        adminRole.setId(1);
        adminRole.setName(ERole.ADMIN);
        Role userRole =new Role();
        userRole.setId(2);
        userRole.setName(ERole.USER);
        roleRepository.save(adminRole);
        roleRepository.save(userRole);
        userRole = roleRepository.findByName(ERole.USER).get();
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        userRepository.save(new User("user", passwordEncoder.encode("user"), roles));
        adminRole = roleRepository.findByName(ERole.ADMIN).get();
        roles = new HashSet<>();
        roles.add(adminRole);
        userRepository.save(new User("admin", passwordEncoder.encode("admin"), roles));
    }
}
