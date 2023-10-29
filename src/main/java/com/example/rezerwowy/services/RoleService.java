package com.example.rezerwowy.services;

import com.example.rezerwowy.exceptions.RoleNotFoundException;
import com.example.rezerwowy.models.Role;
import com.example.rezerwowy.repositories.RoleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Collection<Role> getRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException(id));
    }

    public Role addRole(@Valid Role role) {
        return roleRepository.save(role);
    }

    public void deleteRole(Long id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
        } else {
            throw new RoleNotFoundException(id);
        }
    }
}
