package com.example.rezerwowy.controllers;

import com.example.rezerwowy.dtos.RoleDto;
import com.example.rezerwowy.mappers.RoleMapper;
import com.example.rezerwowy.models.Role;
import com.example.rezerwowy.services.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    private final RoleMapper roleMapper;

    @GetMapping
    public ResponseEntity<Collection<RoleDto>> getRoles() {
        Collection<Role> roles = roleService.getRoles();
        Collection<RoleDto> roleDtos = roles.stream()
                .map(roleMapper::mapRoleToRoleDto)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(roleDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable("id") Long id) {
        Role role = roleService.getRoleById(id);
        RoleDto roleDto = roleMapper.mapRoleToRoleDto(role);

        return ResponseEntity.status(HttpStatus.OK).body(roleDto);
    }

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestBody @Valid RoleDto roleDto) {
        Role roleToCreate = roleMapper.mapRoleDtoToRole(roleDto);
        Role createdRole = roleService.createRole(roleToCreate);
        RoleDto createdRoleDto = roleMapper.mapRoleToRoleDto(createdRole);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable("id") Long id) {
        roleService.deleteRole(id);

        return ResponseEntity.noContent().build();
    }

}
