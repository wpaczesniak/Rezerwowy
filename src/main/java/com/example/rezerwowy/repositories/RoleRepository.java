package com.example.rezerwowy.repositories;

import com.example.rezerwowy.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
