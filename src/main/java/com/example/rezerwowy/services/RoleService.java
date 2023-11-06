package com.example.rezerwowy.services;

import com.example.rezerwowy.exceptions.RoleAlreadyExistsException;
import com.example.rezerwowy.exceptions.RoleNotFoundException;
import com.example.rezerwowy.models.Role;
import com.example.rezerwowy.repositories.RoleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Collection<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException(id));
    }

    @Transactional
    public Role createRole(@Valid Role role) {
        if (roleRepository.existsByName(role.getName())) {
            throw new RoleAlreadyExistsException(role.getName());
        }
        return roleRepository.save(role);
    }

    @Transactional
    public void deleteRole(Long id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
        } else {
            throw new RoleNotFoundException(id);
        }
    }
}
