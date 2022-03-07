package com.banking.assignment.persistence.repistory;

import java.util.Optional;

import com.banking.assignment.persistence.entity.ERole;
import com.banking.assignment.persistence.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
